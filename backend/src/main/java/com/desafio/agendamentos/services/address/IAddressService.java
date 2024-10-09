package com.desafio.agendamentos.services.address;

import com.desafio.agendamentos.entities.Address;
import com.desafio.agendamentos.services.exceptions.AddressNotFoundException;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;

/**
 * Interface para o serviço de endereço.
 * Define métodos para atualizar endereços.
 */
public interface IAddressService {

    /**
     * Atualiza o endereço de um cliente.
     *
     * @param customerId o ID do cliente
     * @param address o endereço a ser atualizado
     * @return o endereço atualizado
     * @throws CustomerNotFoundException se o cliente não for encontrado
     * @throws AddressNotFoundException se o endereço não for encontrado
     */
    Address updateAddress(Long customerId, Address address) throws CustomerNotFoundException, AddressNotFoundException;
}