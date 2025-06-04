package com.freitas.service;

import com.freitas.model.Appointment;
import com.freitas.repo.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    public Appointment scheduleAppointment(Appointment appointment) {
        // Add validation logic here
        return appointmentRepository.save(appointment);
    }
    
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus("CANCELLED");
        appointmentRepository.save(appointment);
    }
}