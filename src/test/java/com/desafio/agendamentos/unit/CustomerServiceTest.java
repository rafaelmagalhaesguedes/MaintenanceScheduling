package com.desafio.agendamentos.unit;

import static org.mockito.ArgumentMatchers.any;

import com.desafio.agendamentos.entities.Customer;
import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.entities.Vehicle;
import com.desafio.agendamentos.enums.Status;
import com.desafio.agendamentos.repositories.CustomerRepository;
import com.desafio.agendamentos.repositories.ScheduleRepository;
import com.desafio.agendamentos.repositories.VehicleRepository;
import com.desafio.agendamentos.services.CepService;
import com.desafio.agendamentos.services.CustomerService;
import com.desafio.agendamentos.services.exceptions.CustomerExistsException;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import com.desafio.agendamentos.services.exceptions.SchedulingDateException;
import com.desafio.agendamentos.services.exceptions.VehicleNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.SchedulingException;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private CustomerService customerService;

    private static final String ERROR_MESSAGE = "Customer not found";

    @Test
    public void createCustomer_WithValidData_ReturnsCustomer() {
        // Arrange
        when(customerRepository.findByEmail(CUSTOMER.getEmail()))
                .thenReturn(Optional.empty());

        when(customerRepository.save(CUSTOMER))
                .thenReturn(CUSTOMER);

        // Act
        var sut = customerService.createCustomer(CUSTOMER);

        // Assert
        assertThat(sut).isEqualTo(CUSTOMER);
    }

    @Test
    public void createCustomer_WithExistingEmail_ThrowsException() {
        // Arrange
        when(customerRepository.findByEmail(CUSTOMER.getEmail()))
                .thenReturn(Optional.of(CUSTOMER));

        // Act & Assert
        assertThatThrownBy(() -> customerService.createCustomer(CUSTOMER))
                .isInstanceOf(CustomerExistsException.class);
    }

    @Test
    public void createCustomer_WithInvalidData_ThrowsException() {
        // Arrange
        when(customerRepository.save(INVALID_CUSTOMER))
                .thenThrow(RuntimeException.class);

        // Act & Assert
        assertThatThrownBy(() -> customerService.createCustomer(INVALID_CUSTOMER))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getCustomer_ByExistingId_ReturnsCustomer() {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId()))
                .thenReturn(Optional.of(CUSTOMER));

        // Act
        Optional<Customer> sut = Optional
                .ofNullable(customerService.findCustomerById(CUSTOMER.getId()));

        // Assert
        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(CUSTOMER);
    }

    @Test
    public void updateCustomer_ExistingCustomer_UpdatesSuccessfully() {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId()))
                .thenReturn(Optional.of(CUSTOMER));

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(CUSTOMER_UPDATED);

        // Act
        Customer result = customerService.updateCustomer(CUSTOMER.getId(), CUSTOMER_UPDATED);

        // Assert
        assertThat(result.getEmail()).isEqualTo(CUSTOMER_UPDATED.getEmail());
        assertThat(result.getNumberPhone()).isEqualTo(CUSTOMER_UPDATED.getNumberPhone());
    }

    @Test
    public void updateCustomer_NonExistentCustomer_ThrowsCustomerNotFoundException() {
        // Arrange
        var ID = CUSTOMER.getId();

        when(customerRepository.findById(ID))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> customerService.updateCustomer(ID, CUSTOMER_UPDATED))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void deleteCustomer_ExistingCustomer_DeletesSuccessfully() {
        // Arrange
        var ID = CUSTOMER.getId();

        when(customerRepository.findById(ID))
                .thenReturn(Optional.of(CUSTOMER));

        // Act
        Optional<Customer> sut = Optional.ofNullable(customerService.deleteCustomer(ID));

        // Assert
        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(CUSTOMER);
    }

    @Test
    public void deleteCustomer_NonExistentCustomer_ThrowsCustomerNotFoundException() {
        // Arrange
        var ID = CUSTOMER.getId();

        when(customerRepository.findById(ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> customerService.deleteCustomer(ID))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void createCustomerVehicle_WithValidData_ReturnsVehicle() throws CustomerNotFoundException {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId()))
                .thenReturn(Optional.of(CUSTOMER));

        when(vehicleRepository.save(VEHICLE))
                .thenReturn(VEHICLE);

        // Act
        var sut = customerService.createCustomerVehicle(CUSTOMER.getId(), VEHICLE);

        // Assert
        assertThat(sut).isEqualTo(VEHICLE);
    }

    @Test
    public void createCustomerVehicle_WithNonExistentCustomer_ThrowsCustomerNotFoundException() {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> customerService.createCustomerVehicle(CUSTOMER.getId(), VEHICLE))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void findVehicleByCustomerId_WithExistingCustomer_ReturnsVehicles() throws CustomerNotFoundException {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.of(CUSTOMER));
        when(vehicleRepository.findByCustomerId(CUSTOMER.getId())).thenReturn(List.of(VEHICLE));

        // Act
        var sut = customerService.findVehicleByCustomerId(CUSTOMER.getId());

        // Assert
        assertThat(sut).isNotEmpty();
        assertThat(sut).contains(VEHICLE);
    }

    @Test
    public void findVehicleByCustomerId_WithNonExistentCustomer_ThrowsCustomerNotFoundException() {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> customerService.findVehicleByCustomerId(CUSTOMER.getId()))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void updateCustomerVehicle_WithValidData_ReturnsUpdatedVehicle() throws CustomerNotFoundException, VehicleNotFoundException {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId()))
                .thenReturn(Optional.of(CUSTOMER));
        when(vehicleRepository.findById(VEHICLE.getId()))
                .thenReturn(Optional.of(VEHICLE));
        when(vehicleRepository.save(any(Vehicle.class)))
                .thenReturn(VEHICLE_UPDATED);

        // Act
        var sut = customerService.updateCustomerVehicle(CUSTOMER.getId(), VEHICLE.getId(), VEHICLE_UPDATED);

        // Assert
        assertThat(sut).isEqualTo(VEHICLE_UPDATED);
    }

    @Test
    public void updateCustomerVehicle_WithNonExistentCustomer_ThrowsCustomerNotFoundException() {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> customerService.updateCustomerVehicle(CUSTOMER.getId(), VEHICLE.getId(), VEHICLE_UPDATED))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void updateCustomerVehicle_WithNonExistentVehicle_ThrowsVehicleNotFoundException() {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId()))
                .thenReturn(Optional.of(CUSTOMER));

        when(vehicleRepository.findById(VEHICLE.getId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> customerService
                .updateCustomerVehicle(CUSTOMER.getId(), VEHICLE.getId(), VEHICLE_UPDATED))
                .isInstanceOf(VehicleNotFoundException.class);
    }

    @Test
    public void deleteCustomerVehicle_WithValidData_DeletesSuccessfully() throws CustomerNotFoundException, VehicleNotFoundException {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId()))
                .thenReturn(Optional.of(CUSTOMER));

        when(vehicleRepository.findById(VEHICLE.getId()))
                .thenReturn(Optional.of(VEHICLE));

        // Act
        var sut = customerService.deleteCustomerVehicle(CUSTOMER.getId(), VEHICLE.getId());

        // Assert
        assertThat(sut).isEqualTo(VEHICLE);
    }

    @Test
    public void deleteCustomerVehicle_WithNonExistentCustomer_ThrowsCustomerNotFoundException() {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> customerService.deleteCustomerVehicle(CUSTOMER.getId(), VEHICLE.getId()))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    public void deleteCustomerVehicle_WithNonExistentVehicle_ThrowsVehicleNotFoundException() {
        // Arrange
        when(customerRepository.findById(CUSTOMER.getId()))
                .thenReturn(Optional.of(CUSTOMER));

        when(vehicleRepository.findById(VEHICLE.getId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> customerService
                .deleteCustomerVehicle(CUSTOMER.getId(), VEHICLE.getId()))
                .isInstanceOf(VehicleNotFoundException.class);
    }

    @Test
    public void createCustomerSchedule_WithValidData_ReturnsSchedule() throws CustomerNotFoundException {
        // Arrange
        var customerId = CUSTOMER.getId();

        var schedule = Schedule.builder()
                .descriptionService("Service Description")
                .dateSchedule(LocalDateTime.now().plusDays(1))
                .build();

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(CUSTOMER));

        when(scheduleRepository.save(any(Schedule.class)))
                .thenReturn(schedule);

        // Act
        Schedule result = customerService.createCustomerSchedule(customerId, schedule);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getDateSchedule()).isEqualTo(schedule.getDateSchedule());
        assertThat(result.getDescriptionService()).isEqualTo(schedule.getDescriptionService());
        assertThat(result.getStatus()).isEqualTo(schedule.getStatus());
        assertThat(result.getCustomer()).isEqualTo(schedule.getCustomer());
    }

    @Test
    public void createCustomerSchedule_WithNonExistentCustomer_ThrowsCustomerNotFoundException() {
        // Arrange
        var customerId = 99999999111223334L;  // ID false
        Schedule schedule = Schedule.builder()
                .descriptionService("Service Description")
                .dateSchedule(LocalDateTime.now().plusDays(1))
                .build();

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> customerService.createCustomerSchedule(customerId, schedule))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    public void createCustomerSchedule_WithNoVehicles_ThrowsVehicleNotFoundException() {
        // Arrange
        var customerId = INVALID_CUSTOMER.getId();

        var schedule = Schedule.builder()
                .descriptionService("Service Description")
                .dateSchedule(LocalDateTime.now().plusDays(1))
                .build();

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(INVALID_CUSTOMER));

        // Act & Assert
        assertThatThrownBy(() -> customerService.createCustomerSchedule(customerId, schedule))
                .isInstanceOf(VehicleNotFoundException.class);
    }

    @Test
    public void createCustomerSchedule_WithInvalidDate_ThrowsException() {
        // Arrange
        var customerId = CUSTOMER.getId();

        var schedule = Schedule.builder()
                .descriptionService("Service Description")
                .dateSchedule(LocalDateTime.now().minusDays(1)) // Invalid date (past date)
                .build();

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(CUSTOMER));

        // Act & Assert
        assertThatThrownBy(() -> customerService.createCustomerSchedule(customerId, schedule))
                .isInstanceOf(SchedulingDateException.class);
    }
}