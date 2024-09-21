package com.desafio.agendamentos.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {

    private String cep;
    private String street;
    private String neighborhood;
    private String city;
    private String state;

    public Address(String cep, String street, String neighborhood, String city, String state) {
        this.cep = cep;
        this.street = street;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
    }
}