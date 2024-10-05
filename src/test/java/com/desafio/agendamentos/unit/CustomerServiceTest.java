package com.desafio.agendamentos.unit;

import com.desafio.agendamentos.entities.Customer;
import com.desafio.agendamentos.repositories.CustomerRepository;
import com.desafio.agendamentos.services.CepService;
import com.desafio.agendamentos.services.customer.CustomerServiceImpl;
import com.desafio.agendamentos.services.exceptions.CustomerExistsException;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.desafio.agendamentos.mocks.CustomerMocks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CepService cepService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private static final String ERROR_MESSAGE = "Customer not found";

    @Test
    public void createCustomer_WithValidData_ReturnsCustomer() {
        // Arrange
        when(customerRepository.findByEmail(CUSTOMER.getEmail()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(CUSTOMER.getPassword()))
                .thenReturn("encodedPassword");

        when(customerRepository.save(CUSTOMER))
                .thenReturn(CUSTOMER);

        doNothing().when(cepService).fillAddress(CUSTOMER);

        // Act
        var result = customerService.createCustomer(CUSTOMER);

        // Assert
        assertThat(result).isEqualTo(CUSTOMER);
    }

    @Test
    public void createCustomer_WithExistingEmail_ThrowsException() {
        // Arrange
        when(customerRepository.findByEmail(CUSTOMER.getEmail())).thenReturn(Optional.of(CUSTOMER));

        // Act & Assert
        assertThatThrownBy(() -> customerService.createCustomer(CUSTOMER))
                .isInstanceOf(CustomerExistsException.class);
    }

    @Test
    public void createCustomer_WithInvalidData_ThrowsException() {
        // Arrange
        when(customerRepository.save(INVALID_CUSTOMER)).thenThrow(RuntimeException.class);

        // Act & Assert
        assertThatThrownBy(() -> customerService.createCustomer(INVALID_CUSTOMER))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getCustomer_ByExistingId_ReturnsCustomer() {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));

        // Act
        var result = customerService.findCustomerById(CUSTOMER.getId());

        // Assert
        assertThat(result).isEqualTo(CUSTOMER);
    }

    @Test
    public void updateCustomer_ExistingCustomer_UpdatesSuccessfully() {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));
        when(customerRepository.save(any(Customer.class))).thenReturn(CUSTOMER_UPDATED);

        // Act
        var result = customerService.updateCustomer(CUSTOMER.getId(), CUSTOMER_UPDATED);

        // Assert
        assertThat(result.getEmail()).isEqualTo(CUSTOMER_UPDATED.getEmail());
        assertThat(result.getNumberPhone()).isEqualTo(CUSTOMER_UPDATED.getNumberPhone());
    }

    @Test
    public void updateCustomer_NonExistentCustomer_ThrowsCustomerNotFoundException() {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> customerService.updateCustomer(CUSTOMER.getId(), CUSTOMER_UPDATED))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void deleteCustomer_ExistingCustomer_DeletesSuccessfully() {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));

        // Act
        var result = customerService.deleteCustomer(CUSTOMER.getId());

        // Assert
        assertThat(result).isEqualTo(CUSTOMER);
    }

    @Test
    public void deleteCustomer_NonExistentCustomer_ThrowsCustomerNotFoundException() {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> customerService.deleteCustomer(CUSTOMER.getId()))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }
}