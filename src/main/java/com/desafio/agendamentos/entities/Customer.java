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
@Builder
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

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> appointments = new ArrayList<>();

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