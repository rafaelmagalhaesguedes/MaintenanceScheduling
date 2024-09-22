package com.desafio.agendamentos.services;

import com.desafio.agendamentos.entities.Address;
import com.desafio.agendamentos.entities.Customer;
import com.desafio.agendamentos.repositories.AddressRepository;
import com.desafio.agendamentos.services.exceptions.AddressNotFoundException;
import com.desafio.agendamentos.services.exceptions.CepNotFoundException;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por gerenciar endereços.
 */
@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final CustomerService customerService;
    private final CepService cepService;

    @Autowired
    public AddressService(AddressRepository addressRepository, CustomerService customerService, CepService cepService) {
        this.addressRepository = addressRepository;
        this.customerService = customerService;
        this.cepService = cepService;
    }

    /**
     * Encontra um endereço pelo seu ID.
     *
     * @param addressId ID do endereço.
     * @return Endereço encontrado.
     * @throws AddressNotFoundException Se o endereço não for encontrado.
     */
    public Address findById(Long addressId) throws AddressNotFoundException {
        return addressRepository.findById(addressId)
                .orElseThrow(AddressNotFoundException::new);
    }

    public Address updateCep(Long customerId, Long addressId, Address cep) throws CustomerNotFoundException, AddressNotFoundException, CepNotFoundException {
        Customer customer = customerService.findById(customerId);
        Address address = findById(addressId);

        address.setCep(address.getCep());

        cepService.fillAddress(customer);

        return address;
    }
}
