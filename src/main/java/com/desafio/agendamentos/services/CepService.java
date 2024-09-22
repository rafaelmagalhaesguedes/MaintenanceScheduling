package com.desafio.agendamentos.services;

import com.desafio.agendamentos.controllers.dtos.AddressResponse;
import com.desafio.agendamentos.entities.Address;
import com.desafio.agendamentos.entities.Customer;
import com.desafio.agendamentos.services.exceptions.CepNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Serviço responsável por buscar CEP do cliente e preencher
 * os dados do endereço automaticamente.
 * Para facilitar o processo utilizamos a API VIACEP.
 */
@Service
public class CepService {

    private static final String VIACEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    /**
     * Preenche os dados de endereço de um cliente a partir do CEP.
     *
     * @param customer Cliente cujo endereço será preenchido.
     * @throws CepNotFoundException Se o CEP não for encontrado.
     */
    public void fillAddress(Customer customer) throws CepNotFoundException
    {
        var restTemplate = new RestTemplate();
        var cep = customer.getAddress().getCep();
        var addressResponse = restTemplate.getForObject(VIACEP_URL, AddressResponse.class, cep);

        if (addressResponse == null || addressResponse.cep() == null) {
            throw new CepNotFoundException();
        }

        var address = getAddress(customer, addressResponse);

        customer.setAddress(address);
    }

    /**
     * Cria um objeto Address a partir dos dados de resposta do serviço de CEP.
     *
     * @param customer        Cliente associado ao endereço.
     * @param addressResponse Resposta do serviço de CEP contendo os dados do endereço.
     * @return Objeto Address preenchido com os dados do endereço.
     */
    private static Address getAddress(Customer customer, AddressResponse addressResponse)
    {
        Address address = new Address();

        address.setCep(addressResponse.cep());
        address.setStreet(addressResponse.logradouro());
        address.setNeighborhood(addressResponse.bairro());
        address.setCity(addressResponse.localidade());
        address.setState(addressResponse.uf());
        address.setCustomer(customer);

        return address;
    }
}