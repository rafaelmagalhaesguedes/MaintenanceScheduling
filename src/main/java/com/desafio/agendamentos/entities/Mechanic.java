package com.desafio.agendamentos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_mechanics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mechanic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "number_phone", nullable = false)
    private String numberPhone;

    @Column(name = "specialty", nullable = false)
    private String specialty;

    @Column(name = "register", nullable = false, unique = true)
    private String register;

    @OneToMany(mappedBy = "mechanic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
}