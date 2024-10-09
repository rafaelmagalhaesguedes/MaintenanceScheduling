package com.desafio.agendamentos.unit;

import com.desafio.agendamentos.entities.Address;
import com.desafio.agendamentos.repositories.AddressRepository;
import com.desafio.agendamentos.services.address.AddressServiceImpl;
import com.desafio.agendamentos.services.customer.CustomerServiceImpl;
import com.desafio.agendamentos.services.exceptions.AddressNotFoundException;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static com.desafio.agendamentos.mocks.CustomerMocks.*;
import static com.desafio.agendamentos.mocks.AddressMocks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AddressServiceTest {

    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    private static final String ERROR_MESSAGE = "Customer not found";

    @Test
    public void updateCustomerAddress_WithValidData_ReturnsUpdatedAddress() throws CustomerNotFoundException, AddressNotFoundException {
        // Arrange
        CUSTOMER.setAddress(ADDRESS);

        when(customerService.findCustomerById(CUSTOMER.getId()))
                .thenReturn(CUSTOMER);

        when(addressRepository.findById(ADDRESS.getId()))
                .thenReturn(Optional.of(ADDRESS));

        when(addressRepository.save(any(Address.class)))
                .thenReturn(ADDRESS_UPDATED);

        // Act
        var result = addressService.updateAddress(CUSTOMER.getId(), ADDRESS_UPDATED);

        // Assert
        assertThat(result).isEqualTo(ADDRESS_UPDATED);
    }

    @Test
    public void updateCustomerAddress_WithNonExistentCustomer_ThrowsCustomerNotFoundException() {
        // Arrange
        when(customerService.findCustomerById(CUSTOMER.getId()))
                .thenThrow(new CustomerNotFoundException());

        // Act & Assert
        assertThatThrownBy(() -> addressService.updateAddress(CUSTOMER.getId(), ADDRESS_UPDATED))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void updateCustomerAddress_WithNonExistentAddress_ThrowsAddressNotFoundException() {
        // Arrange
        when(customerService.findCustomerById(CUSTOMER.getId()))
                .thenReturn(CUSTOMER);

        when(addressRepository.findById(ADDRESS.getId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> addressService.updateAddress(CUSTOMER.getId(), ADDRESS_UPDATED))
                .isInstanceOf(AddressNotFoundException.class);
    }
}