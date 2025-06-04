package com.freitas.exemplo1.controller;

import com.freitas.exemplo1.model.Appointment;
import com.freitas.exemplo1.service.AppointmentService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByOwner(ownerId));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok().build();
    }
}