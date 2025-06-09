package com.freitas.exemplo1.service;

import com.freitas.exemplo1.model.Appointment;
import com.freitas.exemplo1.model.ServiceType;
import com.freitas.exemplo1.repository.AppointmentRepository;
import com.freitas.exemplo1.exception.ResourceNotFoundException;
import com.freitas.exemplo1.exception.InvalidAppointmentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private static final int MAX_SIMULTANEOUS_APPOINTMENTS = 3;
    private static final int MAX_SIMULTANEOUS_CONSULTATIONS = 2;
    private final AppointmentRepository appointmentRepository;
    private final EmailService emailService;

    @Transactional
    public Appointment createAppointment(Appointment appointment) {
        validateAppointment(appointment);
        appointment.setStatus(Appointment.AppointmentStatus.SCHEDULED);
        Appointment saved = appointmentRepository.save(appointment);
        
        // Send confirmation email
        emailService.sendAppointmentConfirmation(
            appointment.getClient().getEmail(),
            appointment.getPet().getName(),
            appointment.getStartTime()
        );
        
        return saved;
    }

    public List<Appointment> getAppointmentsByPet(Long petId) {
        return appointmentRepository.findByPetId(petId);
    }

    public List<Appointment> getAppointmentsByClient(Long clientId) {
        return appointmentRepository.findByClientId(clientId);
    }

    public List<Appointment> getAppointmentsForDay(LocalDateTime day) {
        LocalDateTime startOfDay = day.with(LocalTime.MIN);
        LocalDateTime endOfDay = day.with(LocalTime.MAX);
        return appointmentRepository.findByStartTimeBetween(startOfDay, endOfDay);
    }

    @Transactional
    public void cancelAppointment(Long id, String reason) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
            
        if (appointment.getStatus() == Appointment.AppointmentStatus.CANCELLED) {
            throw new InvalidAppointmentException("Appointment is already cancelled");
        }
        
        appointment.setStatus(Appointment.AppointmentStatus.CANCELLED);
        appointment.setNotes(appointment.getNotes() + "\nCancellation reason: " + reason);
        appointmentRepository.save(appointment);
        
        // Send cancellation email
        emailService.sendAppointmentCancellation(
            appointment.getClient().getEmail(),
            appointment.getPet().getName(),
            appointment.getStartTime()
        );
    }

    @Transactional
    public Appointment rescheduleAppointment(Long id, LocalDateTime newStartTime) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
            
        if (appointment.getStatus() == Appointment.AppointmentStatus.CANCELLED) {
            throw new InvalidAppointmentException("Cannot reschedule cancelled appointment");
        }
        
        // Calculate new end time based on service duration
        LocalDateTime newEndTime = newStartTime.plusMinutes(
            appointment.getServiceType().getDurationMinutes()
        );
        
        appointment.setStartTime(newStartTime);
        appointment.setEndTime(newEndTime);
        appointment.setStatus(Appointment.AppointmentStatus.RESCHEDULED);
        
        validateAppointment(appointment);
        Appointment saved = appointmentRepository.save(appointment);
        
        // Send rescheduling confirmation
        emailService.sendAppointmentReschedule(
            appointment.getClient().getEmail(),
            appointment.getPet().getName(),
            newStartTime
        );
        
        return saved;
    }

    private void validateAppointment(Appointment appointment) {
        if (appointment.getServiceType() == null) {
            throw new InvalidAppointmentException("Service type is required");
        }
        
        validateBusinessHours(appointment.getStartTime());
        validateTimeSlot(appointment);
        
        // Check for slot availability
        int simultaneousAppointments = appointmentRepository
            .countByStartTimeAndStatusNot(
                appointment.getStartTime(), 
                Appointment.AppointmentStatus.CANCELLED
            );
            
        if (simultaneousAppointments >= MAX_SIMULTANEOUS_APPOINTMENTS) {
            throw new InvalidAppointmentException("No available slots at this time");
        }
        
        // Additional validation for consultations
        if (appointment.getServiceType().getName().equals(ServiceType.Type.CONSULTA.name())) {
            int simultaneousConsultations = appointmentRepository
                .countByStartTimeAndServiceTypeNameAndStatusNot(
                    appointment.getStartTime(),
                    ServiceType.Type.CONSULTA.name(),
                    Appointment.AppointmentStatus.CANCELLED
                );
                
            if (simultaneousConsultations >= MAX_SIMULTANEOUS_CONSULTATIONS) {
                throw new InvalidAppointmentException("No veterinarians available at this time");
            }
        }
    }

    private void validateBusinessHours(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        if (time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(18, 0))) {
            throw new InvalidAppointmentException("Appointments must be between 8:00 and 18:00");
        }
    }

    private void validateTimeSlot(Appointment appointment) {
        List<Appointment> conflictingAppointments = appointmentRepository
            .findConflictingAppointments(
                appointment.getId(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                Appointment.AppointmentStatus.CANCELLED
            );
            
        if (!conflictingAppointments.isEmpty()) {
            throw new InvalidAppointmentException("Time slot is already taken");
        }
    }
}