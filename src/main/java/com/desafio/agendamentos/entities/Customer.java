package com.desafio.agendamentos.entities;

import com.desafio.agendamentos.enums.Role;
import com.desafio.agendamentos.services.CryptoService;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CUSTOMER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {

    @Column(name = "numberPhone")
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

    @Builder
    public Customer(String name, String email, String password, Role role, String rawDocument, String numberPhone, Address address) {
        super(name, email, password, role);
        this.numberPhone = numberPhone;
        this.rawDocument = rawDocument;
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