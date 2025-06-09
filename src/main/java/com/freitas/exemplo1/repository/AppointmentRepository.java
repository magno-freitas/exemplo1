package com.freitas.exemplo1.repository;

import com.freitas.exemplo1.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPetId(Long petId);
    
    List<Appointment> findByClientId(Long clientId);
    
    List<Appointment> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    
    int countByStartTimeAndStatusNot(LocalDateTime startTime, Appointment.AppointmentStatus status);
    
    int countByStartTimeAndServiceTypeNameAndStatusNot(
        LocalDateTime startTime,
        String serviceTypeName,
        Appointment.AppointmentStatus status
    );
    
    @Query("SELECT a FROM Appointment a WHERE " +
           "a.id != :appointmentId AND " +
           "a.status != :excludeStatus AND " +
           "((a.startTime BETWEEN :start AND :end) OR " +
           "(a.endTime BETWEEN :start AND :end) OR " +
           "(a.startTime <= :start AND a.endTime >= :end))")
    List<Appointment> findConflictingAppointments(
        @Param("appointmentId") Long appointmentId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        @Param("excludeStatus") Appointment.AppointmentStatus excludeStatus
    );
}