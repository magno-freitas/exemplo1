package com.freitas.exemplo1.service;

import com.freitas.exemplo1.model.Appointment;
import com.freitas.exemplo1.repository.AppointmentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentService {
    
    private final AppointmentRepository appointmentRepository;
    
    public Appointment createAppointment(Appointment appointment) {
        // Add validation logic here
        appointment.setStatus("SCHEDULED");
        appointment.setCreatedAt(LocalDateTime.now());
        return appointmentRepository.save(appointment);
    }
    
    public List<Appointment> getAppointmentsByPet(Long petId) {
        return appointmentRepository.findByPetIdOrderByAppointmentDateTimeDesc(petId);
    }
    
    public List<Appointment> getAppointmentsByClient(Long clientId) {
        return appointmentRepository.findByClientIdOrderByAppointmentDateTimeDesc(clientId);
    }
    
    public List<Appointment> getAppointmentsForDay(LocalDateTime date) {
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        return appointmentRepository.findByAppointmentDateTimeBetweenOrderByAppointmentDateTime(startOfDay, endOfDay);
    }
    
    public void cancelAppointment(Long id, String reason) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus("CANCELLED");
        appointment.setCancellationReason(reason);
        appointment.setCancelledAt(LocalDateTime.now());
        appointmentRepository.save(appointment);
    }
    
    public Appointment rescheduleAppointment(Long id, LocalDateTime newDateTime) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setAppointmentDateTime(newDateTime);
        appointment.setStatus("RESCHEDULED");
        appointment.setLastModifiedAt(LocalDateTime.now());
        return appointmentRepository.save(appointment);
    }
}
