package com.freitas.exemplo1.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(length = 500)
    private String medicalHistory;
}