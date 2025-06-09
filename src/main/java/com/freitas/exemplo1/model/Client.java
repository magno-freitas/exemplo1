package com.freitas.exemplo1.model;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(name = "cpf", length = 11, unique = true)
    private String cpf;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Pet> pets;

    @OneToMany(mappedBy = "client")
    private List<Appointment> appointments;
}
