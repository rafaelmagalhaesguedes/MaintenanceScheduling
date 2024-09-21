package com.desafio.agendamentos.services;

import com.desafio.agendamentos.controllers.dtos.AddressResponse;
import com.desafio.agendamentos.entities.Address;
import com.desafio.agendamentos.entities.Customer;
import com.desafio.agendamentos.services.exceptions.CepNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Address Service
 * *
 * Classe utlizada para obter informações
 * de endereço com base no CEP do cliente.
 * Nesta classe utilizamos a API VIA CEP
 * para simplificar o processo de localização
 * dos endereços dos cliente.
 **/
@Service
public class AddressService {

    private static final String VIACEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    /**
     * Método para preencher o endereço de um cliente com base no CEP informado.
     * @param customer O cliente cujo endereço será preenchido.
     */
    public void fillAddress(Customer customer) throws CepNotFoundException {
        RestTemplate restTemplate = new RestTemplate();

        String cep = customer.getAddress().getCep();

        AddressResponse addressResponse = restTemplate
                .getForObject(VIACEP_URL, AddressResponse.class, cep);

        if (addressResponse == null || addressResponse.cep() == null || "true".equals(addressResponse.error())) {
            throw new CepNotFoundException();
        }

        Address address = new Address();
        address.setCep(addressResponse.cep());
        address.setStreet(addressResponse.logradouro());
        address.setNeighborhood(addressResponse.bairro());
        address.setCity(addressResponse.localidade());
        address.setState(addressResponse.uf());

        customer.setAddress(address);
    }
}