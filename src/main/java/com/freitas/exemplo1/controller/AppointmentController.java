package com.freitas.exemplo1.controller;

import com.freitas.exemplo1.model.Appointment;
import com.freitas.exemplo1.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@Valid @RequestBody Appointment appointment) {
        return ResponseEntity.ok(appointmentService.createAppointment(appointment));
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPet(@PathVariable Long petId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPet(petId));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByClient(clientId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Appointment>> getAppointmentsForDay(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date
    ) {
        return ResponseEntity.ok(appointmentService.getAppointmentsForDay(date));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(
        @PathVariable Long id,
        @RequestParam(required = false) String reason
    ) {
        appointmentService.cancelAppointment(id, reason);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reschedule")
    public ResponseEntity<Appointment> rescheduleAppointment(
        @PathVariable Long id,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newDateTime
    ) {
        return ResponseEntity.ok(appointmentService.rescheduleAppointment(id, newDateTime));
    }
}
