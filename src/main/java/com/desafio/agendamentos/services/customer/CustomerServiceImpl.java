package com.desafio.agendamentos.services.customer;

import com.desafio.agendamentos.entities.Customer;

import com.desafio.agendamentos.repositories.CustomerRepository;

import com.desafio.agendamentos.services.CepService;
import com.desafio.agendamentos.services.exceptions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CepService cepService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CepService cepService) {
        this.customerRepository = customerRepository;
        this.cepService = cepService;
    }

    /**
     * Cria um novo cliente.
     *
     * @param customer Dados do cliente.
     * @return Dados do cliente criado.
     * @throws CustomerExistsException Se o cliente já existir no sistema.
     */
    @Override
    @Transactional
    public Customer createCustomer(Customer customer) throws CustomerExistsException {
        var customerExists = customerRepository.findByEmail(customer.getEmail());

        if (customerExists.isPresent()) {
            throw new CustomerExistsException();
        }

        if (customer.getAddress() != null && customer.getAddress().getCep() != null) {
            cepService.fillAddress(customer);
        }

        return customerRepository.save(customer);
    }

    /**
     * Busca um cliente por ID.
     *
     * @param customerId Id do cliente.
     * @return Dados do cliente encontrado.
     * @throws CustomerNotFoundException Se o cliente não for encontrado.
     */
    @Override
    public Customer findCustomerById(Long customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
    }

    /**
     * Atualiza um cliente.
     *
     * @param customerId Id do cliente.
     * @param customer Dados do cliente.
     * @return Dados do cliente atualizado.
     * @throws CustomerNotFoundException Se o cliente não for encontrado.
     */
    @Override
    @Transactional
    public Customer updateCustomer(Long customerId, Customer customer) throws CustomerNotFoundException {
        var customerFromDb = findCustomerById(customerId);

        customerFromDb.setName(customer.getName());
        customerFromDb.setEmail(customer.getEmail());
        customerFromDb.setNumberPhone(customer.getNumberPhone());

        if (customer.getAddress() != null && customer.getAddress().getCep() != null) {
            cepService.fillAddress(customer);
            customerFromDb.setAddress(customer.getAddress());
        }

        return customerRepository.save(customerFromDb);
    }

    /**
     * Deleta um cliente.
     *
     * @param customerId Id do cliente.
     * @return Dados do cliente deletado.
     * @throws CustomerNotFoundException Se o cliente não for encontrado.
     */
    @Override
    @Transactional
    public Customer deleteCustomer(Long customerId) throws CustomerNotFoundException {
        var customer = findCustomerById(customerId);

        customerRepository.deleteById(customerId);

        return customer;
    }
}