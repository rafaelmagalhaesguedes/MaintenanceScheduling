package com.desafio.agendamentos.controllers.dtos;

public record AddressResponse(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf,
        String error
) { }
