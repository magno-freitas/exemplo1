package com.freitas.exemplo1.model;

import lombok.Data;
import jakarta.persistence.*;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ServiceTypeEnum type;

    public enum ServiceTypeEnum {
        CONSULTA,
        VACINA,
        BANHO,
        TOSA,
        EXAME,
        CIRURGIA
    }

    public ServiceTypeEnum getType() {
        return type;
    }

    public void setType(ServiceTypeEnum type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
