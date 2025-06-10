package com.freitas.exemplo1.service;

import com.freitas.exemplo1.model.Appointment;
import com.freitas.repository.AppointmentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private Appointment appointment;
    private LocalDateTime appointmentDateTime;

    @BeforeEach
    void setUp() {
        appointmentDateTime = LocalDateTime.now().plusDays(1);
        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setClientId(1L);
        appointment.setPetId(1L);
        appointment.setVeterinarianId(1L);
        appointment.setAppointmentDateTime(appointmentDateTime);
        appointment.setServiceType("CHECKUP");
        appointment.setStatus("SCHEDULED");
    }

    @Test
    void createAppointment_Success() {
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment created = appointmentService.createAppointment(appointment);

        assertNotNull(created);
        assertEquals("SCHEDULED", created.getStatus());
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    void getAppointmentsByPet_Success() {
        List<Appointment> appointments = Arrays.asList(appointment);
        when(appointmentRepository.findByPetIdOrderByAppointmentDateTimeDesc(1L)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAppointmentsByPet(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(appointmentRepository).findByPetIdOrderByAppointmentDateTimeDesc(1L);
    }

    @Test
    void cancelAppointment_Success() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        appointmentService.cancelAppointment(1L, "Pet is sick");

        assertEquals("CANCELLED", appointment.getStatus());
        assertNotNull(appointment.getCancellationReason());
        verify(appointmentRepository).save(appointment);
    }

    @Test
    void rescheduleAppointment_Success() {
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(2);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment rescheduled = appointmentService.rescheduleAppointment(1L, newDateTime);

        assertEquals("RESCHEDULED", rescheduled.getStatus());
        assertEquals(newDateTime, rescheduled.getAppointmentDateTime());
        verify(appointmentRepository).save(appointment);
    }
}