package com.desafio.agendamentos.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_addresses")
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cep;
    private String street;
    private String neighborhood;
    private String city;
    private String state;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Address(String cep, String logradouro, String bairro, String localidade, String uf) {
        this.cep = cep;
        this.street = logradouro;
        this.neighborhood = bairro;
        this.city = localidade;
        this.state = uf;
    }
}