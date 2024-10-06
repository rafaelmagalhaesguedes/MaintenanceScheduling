package com.desafio.agendamentos.services.address;

import com.desafio.agendamentos.entities.Address;
import com.desafio.agendamentos.repositories.AddressRepository;
import com.desafio.agendamentos.services.customer.ICustomerService;
import com.desafio.agendamentos.services.exceptions.AddressNotFoundException;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementação da interface IAddressService.
 * Fornece métodos para atualizar endereços.
 */
@Service
public class AddressServiceImpl implements IAddressService {

    private final AddressRepository addressRepository;
    private final ICustomerService customerService;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, ICustomerService customerService) {
        this.addressRepository = addressRepository;
        this.customerService = customerService;
    }

    /**
     * Atualiza o endereço de um cliente.
     *
     * @param customerId o ID do cliente
     * @param address o endereço a ser atualizado
     * @return o endereço atualizado
     * @throws CustomerNotFoundException se o cliente não for encontrado
     * @throws AddressNotFoundException se o endereço não for encontrado
     */
    @Override
    @Transactional
    public Address updateAddress(Long customerId, Address address) throws CustomerNotFoundException, AddressNotFoundException {
        var customer = customerService.findCustomerById(customerId);
        var addressId = customer.getAddress().getId();
        var addressFromDb = addressRepository.findById(addressId).orElseThrow(AddressNotFoundException::new);

        addressFromDb.setCep(address.getCep());
        addressFromDb.setStreet(address.getStreet());
        addressFromDb.setNeighborhood(address.getNeighborhood());
        addressFromDb.setCity(address.getCity());
        addressFromDb.setState(address.getState());

        return addressRepository.save(addressFromDb);
    }
}