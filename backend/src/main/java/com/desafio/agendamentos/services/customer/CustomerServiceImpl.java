package com.desafio.agendamentos.services.customer;

import com.desafio.agendamentos.entities.Customer;

import com.desafio.agendamentos.enums.UserRole;
import com.desafio.agendamentos.repositories.CustomerRepository;

import com.desafio.agendamentos.services.CepService;
import com.desafio.agendamentos.services.exceptions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CepService cepService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CepService cepService, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.cepService = cepService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Cria um novo cliente.
     *
     * @param customer Dados do cliente.
     * @return Dados do cliente criado.
     * @throws UserExistsException Se o cliente já existir no sistema.
     */
    @Override
    @Transactional
    public Customer createCustomer(Customer customer) throws UserExistsException {
        var customerExists = customerRepository.findByEmail(customer.getEmail());

        if (customerExists.isPresent()) {
            throw new UserExistsException();
        }

        if (customer.getAddress() != null && customer.getAddress().getCep() != null) {
            cepService.fillAddress(customer);
        }

        var encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        customer.setUserRole(UserRole.CUSTOMER);

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