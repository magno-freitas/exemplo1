package com.freitas.exemplo1.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServiceType serviceType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    @Column(precision = 10, scale = 2)
    private Double price;

    @Column(length = 500)
    private String notes;

    public enum AppointmentStatus {
        SCHEDULED, COMPLETED, CANCELLED, RESCHEDULED
    }

    // Helper method to set both sides of the relationship
    public void setPet(Pet pet) {
        this.pet = pet;
        this.client = pet != null ? pet.getOwner() : null;
    }
}