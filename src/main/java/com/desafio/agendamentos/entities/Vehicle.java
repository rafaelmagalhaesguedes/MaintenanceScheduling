package com.desafio.agendamentos.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String licensePlate;

    private String model;

    private String make;

    private Integer year;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Vehicle(String licensePlate, String model, String make, Integer year) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.make = make;
        this.year = year;
    }

    public Vehicle(Long id, String licensePlate, String model, String make, Integer year) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.model = model;
        this.make = make;
        this.year = year;
    }
}