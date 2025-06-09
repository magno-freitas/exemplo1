package com.freitas.exemplo1.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String species;

    private String breed;

    @Column(nullable = false)
    private LocalDate birthDate;

    private Double weight;

    @Column(length = 1000)
    private String medicalHistory;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client owner;

    @OneToMany(mappedBy = "pet")
    private List<Appointment> appointments;
}