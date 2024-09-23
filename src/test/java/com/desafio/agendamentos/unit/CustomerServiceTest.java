package com.desafio.agendamentos.unit;

import static org.mockito.ArgumentMatchers.any;

import com.desafio.agendamentos.entities.Customer;
import com.desafio.agendamentos.entities.Vehicle;
import com.desafio.agendamentos.repositories.CustomerRepository;
import com.desafio.agendamentos.repositories.VehicleRepository;
import com.desafio.agendamentos.services.CepService;
import com.desafio.agendamentos.services.CustomerService;
import com.desafio.agendamentos.services.exceptions.CustomerExistsException;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import com.desafio.agendamentos.services.exceptions.VehicleNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.desafio.agendamentos.mocks.CustomerMocks.*;
import static com.desafio.agendamentos.mocks.VehicleMocks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CepService cepService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private CustomerService customerService;

    private static final String ERROR_MESSAGE = "Customer not found";

    @Test
    public void createCustomer_WithValidData_ReturnsCustomer() {
        when(customerRepository.findByEmail(CUSTOMER.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.save(CUSTOMER)).thenReturn(CUSTOMER);

        var sut = customerService.createCustomer(CUSTOMER);

        assertThat(sut).isEqualTo(CUSTOMER);
        verify(customerRepository, times(1)).save(CUSTOMER);
    }

    @Test
    public void createCustomer_WithExistingEmail_ThrowsException() {
        when(customerRepository.findByEmail(CUSTOMER.getEmail()))
                .thenReturn(Optional.of(CUSTOMER));

        assertThatThrownBy(() -> customerService.createCustomer(CUSTOMER))
                .isInstanceOf(CustomerExistsException.class);

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    public void createCustomer_WithInvalidData_ThrowsException() {
        when(customerRepository.save(INVALID_CUSTOMER)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> customerService.createCustomer(INVALID_CUSTOMER))
                .isInstanceOf(RuntimeException.class);

        verify(customerRepository, times(1)).save(INVALID_CUSTOMER);
    }

    @Test
    public void getCustomer_ByExistingId_ReturnsCustomer() {
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));

        Optional<Customer> sut = Optional.ofNullable(customerService.findCustomerById(CUSTOMER.getId()));

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(CUSTOMER);
    }

    @Test
    public void updateCustomer_ExistingCustomer_UpdatesSuccessfully() {
        var ID = CUSTOMER.getId();

        when(customerRepository.findById(ID)).thenReturn(Optional.of(CUSTOMER));
        when(customerRepository.save(any(Customer.class))).thenReturn(CUSTOMER_UPDATED);

        Customer result = customerService.updateCustomer(ID, CUSTOMER_UPDATED);

        assertThat(result.getEmail()).isEqualTo(CUSTOMER_UPDATED.getEmail());
        assertThat(result.getNumberPhone()).isEqualTo(CUSTOMER_UPDATED.getNumberPhone());
    }

    @Test
    public void updateCustomer_NonExistentCustomer_ThrowsCustomerNotFoundException() {
        var ID = CUSTOMER.getId();

        when(customerRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.updateCustomer(ID, CUSTOMER_UPDATED))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void deleteCustomer_ExistingCustomer_DeletesSuccessfully() {
        var ID = CUSTOMER.getId();

        when(customerRepository.findById(ID)).thenReturn(Optional.of(CUSTOMER));
        doNothing().when(customerRepository).deleteById(ID);

        Optional<Customer> sut = Optional.ofNullable(customerService.deleteCustomer(ID));

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(CUSTOMER);
    }

    @Test
    public void deleteCustomer_NonExistentCustomer_ThrowsCustomerNotFoundException() {
        var ID = CUSTOMER.getId();

        when(customerRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.deleteCustomer(ID))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void createCustomerVehicle_WithValidData_ReturnsVehicle() throws CustomerNotFoundException {
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));
        when(vehicleRepository.save(VEHICLE)).thenReturn(VEHICLE);

        var sut = customerService.createCustomerVehicle(CUSTOMER.getId(), VEHICLE);

        assertThat(sut).isEqualTo(VEHICLE);
        verify(vehicleRepository, times(1)).save(VEHICLE);
    }

    @Test
    public void createCustomerVehicle_WithNonExistentCustomer_ThrowsCustomerNotFoundException() {
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.createCustomerVehicle(CUSTOMER.getId(), VEHICLE))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);

        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    public void findVehicleByCustomerId_WithExistingCustomer_ReturnsVehicles() throws CustomerNotFoundException {
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));
        when(vehicleRepository.findByCustomerId(CUSTOMER.getId())).thenReturn(List.of(VEHICLE));

        var sut = customerService.findVehicleByCustomerId(CUSTOMER.getId());

        assertThat(sut).isNotEmpty();
        assertThat(sut).contains(VEHICLE);
    }

    @Test
    public void findVehicleByCustomerId_WithNonExistentCustomer_ThrowsCustomerNotFoundException() {
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.findVehicleByCustomerId(CUSTOMER.getId()))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void updateCustomerVehicle_WithValidData_ReturnsUpdatedVehicle() throws CustomerNotFoundException, VehicleNotFoundException {
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));
        when(vehicleRepository.findById(VEHICLE.getId())).thenReturn(Optional.of(VEHICLE));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(VEHICLE_UPDATED);

        var sut = customerService.updateCustomerVehicle(CUSTOMER.getId(), VEHICLE.getId(), VEHICLE_UPDATED);

        assertThat(sut).isEqualTo(VEHICLE_UPDATED);
    }

    @Test
    public void updateCustomerVehicle_WithNonExistentCustomer_ThrowsCustomerNotFoundException() {
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.updateCustomerVehicle(CUSTOMER.getId(), VEHICLE.getId(), VEHICLE_UPDATED))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);

        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    public void updateCustomerVehicle_WithNonExistentVehicle_ThrowsVehicleNotFoundException() {
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));
        when(vehicleRepository.findById(VEHICLE.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.updateCustomerVehicle(CUSTOMER.getId(), VEHICLE.getId(), VEHICLE_UPDATED))
                .isInstanceOf(VehicleNotFoundException.class);

        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    public void deleteCustomerVehicle_WithValidData_DeletesSuccessfully() throws CustomerNotFoundException, VehicleNotFoundException {
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));
        when(vehicleRepository.findById(VEHICLE.getId())).thenReturn(Optional.of(VEHICLE));
        doNothing().when(vehicleRepository).deleteById(VEHICLE.getId());

        var sut = customerService.deleteCustomerVehicle(CUSTOMER.getId(), VEHICLE.getId());

        assertThat(sut).isEqualTo(VEHICLE);
        verify(vehicleRepository, times(1)).deleteById(VEHICLE.getId());
    }

    @Test
    public void deleteCustomerVehicle_WithNonExistentCustomer_ThrowsCustomerNotFoundException() {
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.deleteCustomerVehicle(CUSTOMER.getId(), VEHICLE.getId()))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);

        verify(vehicleRepository, never()).deleteById(anyLong());
    }

    @Test
    public void deleteCustomerVehicle_WithNonExistentVehicle_ThrowsVehicleNotFoundException() {
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));
        when(vehicleRepository.findById(VEHICLE.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.deleteCustomerVehicle(CUSTOMER.getId(), VEHICLE.getId()))
                .isInstanceOf(VehicleNotFoundException.class);

        verify(vehicleRepository, never()).deleteById(anyLong());
    }
}
