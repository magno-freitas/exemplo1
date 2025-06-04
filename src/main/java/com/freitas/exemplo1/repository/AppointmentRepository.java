package com.freitas.exemplo1.repository;

import com.freitas.exemplo1.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPetId(Long petId);
    List<Appointment> findByAppointmentTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Appointment> findByPetOwnerId(Long ownerId);
}