package com.desafio.agendamentos.unit;

import static org.mockito.ArgumentMatchers.any;

import com.desafio.agendamentos.entities.Customer;
import com.desafio.agendamentos.repositories.CustomerRepository;
import com.desafio.agendamentos.services.CustomerService;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.desafio.agendamentos.mocks.CustomerMocks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @InjectMocks
    private CustomerService service;

    @Mock
    private CustomerRepository repository;

    private static final String ERROR_MESSAGE = "Customer not found";

    @Test
    public void createCustomer_WithValidData_ReturnsCustomer() {
        when(repository.findByEmail(CUSTOMER.getEmail())).thenReturn(Optional.empty());
        when(repository.save(CUSTOMER)).thenReturn(CUSTOMER);

        var sut = service.create(CUSTOMER);

        assertThat(sut).isEqualTo(CUSTOMER);
    }

    @Test
    public void createCustomer_WithInvalidData_ThrowsException() {
        when(repository.save(INVALID_CUSTOMER)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> service.create(INVALID_CUSTOMER))
                .isInstanceOf(RuntimeException.class);

    }

    @Test
    public void getCustomer_ByExistingId_ReturnsCustomer() {
        when(repository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));

        Optional<Customer> sut = Optional.ofNullable(service.findById(CUSTOMER.getId()));

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(CUSTOMER);
    }

    @Test
    public void updateCustomer_ExistingCustomer_UpdatesSuccessfully() {
        var ID = CUSTOMER.getId();

        when(repository.findById(ID)).thenReturn(Optional.of(CUSTOMER));
        when(repository.save(any(Customer.class))).thenReturn(CUSTOMER_UPDATED);

        Customer result = service.update(ID, CUSTOMER_UPDATED);

        assertThat(result.getEmail()).isEqualTo(CUSTOMER_UPDATED.getEmail());
        assertThat(result.getNumberPhone()).isEqualTo(CUSTOMER_UPDATED.getNumberPhone());
    }

    @Test
    public void updateCustomer_NonExistentCustomer_ThrowsCustomerNotFoundException() {
        var ID = CUSTOMER.getId();

        when(repository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(ID, CUSTOMER_UPDATED))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void deleteCustomer_ExistingCustomer_DeletesSuccessfully() {
        var ID = CUSTOMER.getId();

        when(repository.findById(ID)).thenReturn(Optional.of(CUSTOMER));
        doNothing().when(repository).deleteById(ID);

        Optional<Customer> sut = Optional.ofNullable(service.delete(ID));

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(CUSTOMER);
    }

    @Test
    public void deleteCustomer_NonExistentCustomer_ThrowsCustomerNotFoundException() {
        var ID = CUSTOMER.getId();

        when(repository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(ID))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void listCustomers_WithValidPageable_ReturnsPagedCustomers() {
        int pageNumber = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Customer> customers = List.of(C1, C2);
        Page<Customer> page = new PageImpl<>(customers, pageable, customers.size());

        when(repository.findAll(pageable)).thenReturn(page);

        List<Customer> result = service.list(pageNumber, pageSize);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(C1, C2);
        verify(repository).findAll(pageable);
    }

    @Test
    public void listCustomers_WithEmptyPage_ReturnsEmptyList() {
        int pageNumber = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Customer> page = Page.empty(pageable);

        when(repository.findAll(pageable)).thenReturn(page);

        List<Customer> result = service.list(pageNumber, pageSize);

        assertThat(result).isEmpty();
        verify(repository).findAll(pageable);
    }
}
