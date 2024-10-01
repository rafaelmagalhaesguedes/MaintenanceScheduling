package com.desafio.agendamentos.unit;

import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.Status;
import com.desafio.agendamentos.repositories.ScheduleRepository;
import com.desafio.agendamentos.services.ScheduleService;
import com.desafio.agendamentos.services.exceptions.ScheduleNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    void findScheduleById_WithValidId_ReturnsSchedule() throws ScheduleNotFoundException {
        // Arrange
        var scheduleId = 1L;
        var schedule = Schedule
                .builder()
                .id(scheduleId)
                .build();

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

        // Act
        var result = scheduleService.findScheduleById(scheduleId);

        // Assert
        assertEquals(scheduleId, result.getId());
    }

    @Test
    void findScheduleById_WithInvalidId_ThrowsScheduleNotFoundException() {
        // Arrange
        var scheduleId = 1L;

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ScheduleNotFoundException.class, () -> scheduleService.findScheduleById(scheduleId));
    }

    @Test
    void updateScheduleStatus_WithValidId_ReturnsUpdatedSchedule() throws ScheduleNotFoundException {
        // Arrange
        var scheduleId = 1L;
        var schedule = Schedule.builder()
                .id(scheduleId)
                .status(Status.PENDENTE)
                .build();

        when(scheduleRepository.findById(scheduleId))
                .thenReturn(Optional.of(schedule));

        when(scheduleRepository.save(any(Schedule.class)))
                .thenReturn(schedule);

        // Act
        var result = scheduleService.updateScheduleStatus(scheduleId);

        // Assert
        assertEquals(Status.REALIZADO, result.getStatus());
    }

    @Test
    void updateScheduleStatus_WithInvalidId_ThrowsScheduleNotFoundException() {
        // Arrange
        var scheduleId = 1L;

        when(scheduleRepository.findById(scheduleId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ScheduleNotFoundException.class, () -> scheduleService
                .updateScheduleStatus(scheduleId));
    }

    @Test
    void listSchedulings_ReturnsPageOfSchedules() {
        // Arrange
        var page = 0;
        var pageSize = 10;
        var sort = Sort.by(Sort.Direction.ASC, "dateSchedule");
        var pageRequest = PageRequest.of(page, pageSize, sort);
        var schedule = new Schedule();
        Page<Schedule> schedulePage = new PageImpl<>(Collections.singletonList(schedule));

        when(scheduleRepository.findAll(pageRequest))
                .thenReturn(schedulePage);

        // Act
        Page<Schedule> result = scheduleService.listSchedulings(page, pageSize);

        // Assert
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void listSchedulingsByStatus_ReturnsPageOfSchedules() {
        // Arrange
        var page = 0;
        var pageSize = 10;
        var status = Status.PENDENTE;
        var sort = Sort.by(Sort.Direction.ASC, "dateSchedule");
        var pageRequest = PageRequest.of(page, pageSize, sort);
        var schedule = Schedule.builder()
                .status(status)
                .build();

        Page<Schedule> schedulePage = new PageImpl<>(Collections.singletonList(schedule));

        when(scheduleRepository.findByStatus(status, pageRequest))
                .thenReturn(schedulePage);

        // Act
        Page<Schedule> result = scheduleService
                .listSchedulingsByStatus(status, page, pageSize);

        // Assert
        assertEquals(1, result.getTotalElements());
    }
}
