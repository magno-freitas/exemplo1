package com.freitas.exemplo1.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "service_types")
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(precision = 10, scale = 2, nullable = false)
    private Double basePrice;

    @Column(nullable = false)
    private Integer durationMinutes;

    public enum Type {
        CONSULTA,
        VACINA,
        BANHO,
        TOSA,
        EXAME,
        CIRURGIA
    }
}
