package com.freitas.exemplo1.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServiceType serviceType;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String notes;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    public enum AppointmentStatus {
        SCHEDULED, COMPLETED, CANCELLED
    }
}