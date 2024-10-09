package com.desafio.agendamentos.unit;

import com.desafio.agendamentos.entities.Vehicle;
import com.desafio.agendamentos.repositories.CustomerRepository;
import com.desafio.agendamentos.repositories.VehicleRepository;
import com.desafio.agendamentos.services.customer.ICustomerService;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import com.desafio.agendamentos.services.exceptions.VehicleNotFoundException;
import com.desafio.agendamentos.services.vehicle.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static com.desafio.agendamentos.mocks.CustomerMocks.*;
import static com.desafio.agendamentos.mocks.VehicleMocks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class VehicleServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private ICustomerService customerService;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private static final String ERROR_MESSAGE = "Customer not found";

    @Test
    public void createCustomerVehicle_WithValidData_ReturnsVehicle() throws CustomerNotFoundException {
        when(customerService.findCustomerById(CUSTOMER.getId())).thenReturn(CUSTOMER);
        when(vehicleRepository.save(VEHICLE)).thenReturn(VEHICLE);

        var result = vehicleService.createVehicle(CUSTOMER.getId(), VEHICLE);

        assertThat(result).isEqualTo(VEHICLE);
    }

    @Test
    public void createCustomerVehicle_WithNonExistentCustomer_ThrowsCustomerNotFoundException() {
        when(customerService.findCustomerById(CUSTOMER.getId())).thenThrow(new CustomerNotFoundException());

        assertThatThrownBy(() -> vehicleService.createVehicle(CUSTOMER.getId(), VEHICLE))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void findVehicleByCustomerId_WithExistingCustomer_ReturnsVehicles() throws CustomerNotFoundException {
        when(customerService.findCustomerById(CUSTOMER.getId())).thenReturn(CUSTOMER);
        when(vehicleRepository.findByCustomerId(CUSTOMER.getId())).thenReturn(List.of(VEHICLE));

        var result = vehicleService.findVehiclesByCustomerId(CUSTOMER.getId());

        assertThat(result).isNotEmpty();
        assertThat(result).contains(VEHICLE);
    }

    @Test
    public void findVehicleByCustomerId_WithNonExistentCustomer_ThrowsCustomerNotFoundException() {
        when(customerService.findCustomerById(CUSTOMER.getId())).thenThrow(new CustomerNotFoundException());

        assertThatThrownBy(() -> vehicleService.findVehiclesByCustomerId(CUSTOMER.getId()))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void updateCustomerVehicle_WithValidData_ReturnsUpdatedVehicle() throws CustomerNotFoundException, VehicleNotFoundException {
        when(customerService.findCustomerById(CUSTOMER.getId())).thenReturn(CUSTOMER);
        when(vehicleRepository.findById(VEHICLE.getId())).thenReturn(Optional.of(VEHICLE));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(VEHICLE_UPDATED);

        var result = vehicleService.updateVehicle(CUSTOMER.getId(), VEHICLE.getId(), VEHICLE_UPDATED);

        assertThat(result).isEqualTo(VEHICLE_UPDATED);
    }

    @Test
    public void updateCustomerVehicle_WithNonExistentCustomer_ThrowsCustomerNotFoundException() {
        when(customerService.findCustomerById(CUSTOMER.getId())).thenThrow(new CustomerNotFoundException());

        assertThatThrownBy(() -> vehicleService.updateVehicle(CUSTOMER.getId(), VEHICLE.getId(), VEHICLE_UPDATED))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void updateCustomerVehicle_WithNonExistentVehicle_ThrowsVehicleNotFoundException() {
        when(customerService.findCustomerById(CUSTOMER.getId())).thenReturn(CUSTOMER);
        when(vehicleRepository.findById(VEHICLE.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> vehicleService.updateVehicle(CUSTOMER.getId(), VEHICLE.getId(), VEHICLE_UPDATED))
                .isInstanceOf(VehicleNotFoundException.class);
    }

    @Test
    public void deleteCustomerVehicle_WithValidData_DeletesSuccessfully() throws CustomerNotFoundException, VehicleNotFoundException {
        when(customerService.findCustomerById(CUSTOMER.getId())).thenReturn(CUSTOMER);
        when(vehicleRepository.findById(VEHICLE.getId())).thenReturn(Optional.of(VEHICLE));

        var result = vehicleService.deleteVehicle(CUSTOMER.getId(), VEHICLE.getId());

        assertThat(result).isEqualTo(VEHICLE);
    }

    @Test
    public void deleteCustomerVehicle_WithNonExistentCustomer_ThrowsCustomerNotFoundException() {
        when(customerService.findCustomerById(CUSTOMER.getId())).thenThrow(new CustomerNotFoundException());

        assertThatThrownBy(() -> vehicleService.deleteVehicle(CUSTOMER.getId(), VEHICLE.getId()))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void deleteCustomerVehicle_WithNonExistentVehicle_ThrowsVehicleNotFoundException() {
        when(customerService.findCustomerById(CUSTOMER.getId())).thenReturn(CUSTOMER);
        when(vehicleRepository.findById(VEHICLE.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> vehicleService.deleteVehicle(CUSTOMER.getId(), VEHICLE.getId()))
                .isInstanceOf(VehicleNotFoundException.class);
    }
}