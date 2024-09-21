package com.desafio.agendamentos.entities;

import com.desafio.agendamentos.services.CryptoService;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String numberPhone;

    @Column(name = "document")
    private String encryptedDocument;

    @Transient
    private String rawDocument;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> appointments = new ArrayList<>();

    public Customer(String name, String email, String numberPhone, String document) {
        this.name = name;
        this.email = email;
        this.numberPhone = numberPhone;
        this.rawDocument = document;
    }

    public Customer(Long id, String name, String email, String numberPhone, String document) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.numberPhone = numberPhone;
        this.rawDocument = document;
    }

    public Customer(String name, String email, String numberPhone, String document, Address address) {
        this.name = name;
        this.email = email;
        this.numberPhone = numberPhone;
        this.rawDocument = document;
        this.address = address;
    }

    @PrePersist
    @PreUpdate
    public void encryptFields() {
        this.encryptedDocument = CryptoService.encrypt(rawDocument);
    }

    @PostLoad
    public void decryptFields() {
        this.rawDocument = CryptoService.decrypt(encryptedDocument);
    }
}