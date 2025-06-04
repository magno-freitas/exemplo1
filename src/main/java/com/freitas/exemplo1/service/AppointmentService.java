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
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public Appointment createAppointment(Appointment appointment) {
        // Add validation logic here
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByPet(Long petId) {
        return appointmentRepository.findByPetId(petId);
    }

    public List<Appointment> getAppointmentsByOwner(Long ownerId) {
        return appointmentRepository.findByPetOwnerId(ownerId);
    }

    public List<Appointment> getAppointmentsForTimeRange(LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByAppointmentTimeBetween(start, end);
    }

    @Transactional
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus(Appointment.AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }
}