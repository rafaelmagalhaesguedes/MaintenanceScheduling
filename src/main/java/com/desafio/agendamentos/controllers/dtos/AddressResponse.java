package com.desafio.agendamentos.controllers.dtos;

import com.desafio.agendamentos.entities.Address;

public record AddressResponse(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf
) {
    public static AddressResponse fromEntity(Address address) {
        return new AddressResponse(
            address.getCep(),
            address.getStreet(),
            address.getNeighborhood(),
            address.getCity(),
            address.getState()
        );
    }
}
