package com.desafio.agendamentos.services;

import com.desafio.agendamentos.entities.Customer;
import com.desafio.agendamentos.repositories.CustomerRepository;
import com.desafio.agendamentos.services.exceptions.CustomerExistsException;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(Customer customer) throws CustomerExistsException {
        var customerExists = customerRepository.findByEmail(customer.getEmail());

        if (customerExists.isPresent()) {
            throw new CustomerExistsException();
        }

        return customerRepository.save(customer);
    }

    public Customer findById(Long customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
    }

    public Customer update(Long customerId, Customer customer) throws CustomerNotFoundException {
        var customerFromDb = findById(customerId);

        customerFromDb.setName(customer.getName());
        customerFromDb.setEmail(customer.getEmail());
        customerFromDb.setNumberPhone(customer.getNumberPhone());

        return customerRepository.save(customerFromDb);
    }

    public Customer delete(Long customerId) throws CustomerNotFoundException {
        var customer = findById(customerId);

        customerRepository.deleteById(customerId);

        return customer;
    }

    public List<Customer> list(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Customer> page = customerRepository.findAll(pageable);

        return page.toList();
    }
}
