package com.desafio.agendamentos.unit;

import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.Status;
import com.desafio.agendamentos.repositories.ScheduleRepository;
import com.desafio.agendamentos.services.customer.CustomerServiceImpl;
import com.desafio.agendamentos.services.exceptions.*;
import com.desafio.agendamentos.services.schedule.ScheduleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.desafio.agendamentos.mocks.CustomerMocks.*;
import static com.desafio.agendamentos.mocks.ScheduleMocks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private CustomerServiceImpl customerService;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Test
    public void createCustomerSchedule_WithValidData_ReturnsSchedule() throws CustomerNotFoundException {
        // Arrange
        var customerId = CUSTOMER.getId();

        when(customerService.findCustomerById(customerId)).thenReturn(CUSTOMER);
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(SCHEDULE);

        // Act
        var result = scheduleService.createSchedule(customerId, SCHEDULE);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getDateSchedule()).isEqualTo(SCHEDULE.getDateSchedule());
        assertThat(result.getDescriptionService()).isEqualTo(SCHEDULE.getDescriptionService());
        assertThat(result.getStatus()).isEqualTo(SCHEDULE.getStatus());
        assertThat(result.getCustomer()).isEqualTo(SCHEDULE.getCustomer());
    }

    @Test
    public void createCustomerSchedule_WithNonExistentCustomer_ThrowsCustomerNotFoundException() {
        // Arrange
        var customerId = 99999999111223334L;  // Invalid ID

        when(customerService.findCustomerById(customerId))
                .thenThrow(new CustomerNotFoundException());

        // Act & Assert
        assertThatThrownBy(() -> scheduleService.createSchedule(customerId, SCHEDULE))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    public void createCustomerSchedule_WithNoVehicles_ThrowsVehicleNotFoundException() {
        // Arrange
        var customerId = INVALID_CUSTOMER.getId();

        when(customerService.findCustomerById(customerId))
                .thenThrow(new VehicleNotFoundException());

        // Act & Assert
        assertThatThrownBy(() -> scheduleService.createSchedule(customerId, SCHEDULE))
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

        when(customerService.findCustomerById(customerId))
                .thenThrow(new SchedulingDateException("Customer not found."));

        // Act & Assert
        assertThatThrownBy(() -> scheduleService.createSchedule(customerId, schedule))
                .isInstanceOf(SchedulingDateException.class);
    }

    @Test
    public void cancelCustomerSchedule_WithValidData_ReturnsCancelledSchedule() {
        // Arrange
        var customerId = CUSTOMER.getId();
        var scheduleId = SCHEDULE.getId();
        var schedule = SCHEDULE;

        schedule.setCustomer(CUSTOMER);

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        // Act
        var result = scheduleService.cancelSchedule(customerId, scheduleId, Status.CANCELADO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(Status.CANCELADO);
    }

    @Test
    public void cancelCustomerSchedule_WithNonExistentSchedule_ThrowsScheduleNotFoundException() {
        // Arrange
        var customerId = CUSTOMER.getId();
        var scheduleId = 9999922211188900L; // Invalid ID

        when(scheduleRepository.findById(scheduleId)).thenThrow(new ScheduleNotFoundException());

        // Act & Assert
        assertThatThrownBy(() -> scheduleService.cancelSchedule(customerId, scheduleId, Status.CANCELADO))
                .isInstanceOf(ScheduleNotFoundException.class);
    }

    @Test
    public void cancelCustomerSchedule_WithInvalidStatus_ThrowsStatusValidateException() {
        // Arrange
        var customerId = CUSTOMER.getId();
        var scheduleId = SCHEDULE.getId();
        var schedule = SCHEDULE;

        schedule.setCustomer(CUSTOMER);

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

        // Act & Assert
        assertThatThrownBy(() -> scheduleService.cancelSchedule(customerId, scheduleId, Status.REALIZADO))
                .isInstanceOf(StatusValidateException.class);
    }
}