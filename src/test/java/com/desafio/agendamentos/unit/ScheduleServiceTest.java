package com.desafio.agendamentos.unit;

import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.Status;
import com.desafio.agendamentos.repositories.ScheduleRepository;
import com.desafio.agendamentos.services.ScheduleService;
import com.desafio.agendamentos.services.exceptions.ScheduleNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
        Long scheduleId = 1L;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

        // Act
        Schedule result = scheduleService.findScheduleById(scheduleId);

        // Assert
        assertEquals(scheduleId, result.getId());
        verify(scheduleRepository).findById(scheduleId);
    }

    @Test
    void findScheduleById_WithInvalidId_ThrowsScheduleNotFoundException() {
        // Arrange
        Long scheduleId = 1L;

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ScheduleNotFoundException.class, () -> scheduleService.findScheduleById(scheduleId));
        verify(scheduleRepository).findById(scheduleId);
    }

    @Test
    void updateScheduleStatus_WithValidId_ReturnsUpdatedSchedule() throws ScheduleNotFoundException {
        // Arrange
        Long scheduleId = 1L;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setStatus(Status.PENDENTE);

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        // Act
        Schedule result = scheduleService.updateScheduleStatus(scheduleId, Status.REALIZADO);

        // Assert
        assertEquals(Status.REALIZADO, result.getStatus());
        verify(scheduleRepository).findById(scheduleId);
        verify(scheduleRepository).save(schedule);
    }

    @Test
    void updateScheduleStatus_WithInvalidId_ThrowsScheduleNotFoundException() {
        // Arrange
        Long scheduleId = 1L;

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ScheduleNotFoundException.class, () -> scheduleService.updateScheduleStatus(scheduleId, Status.REALIZADO));
        verify(scheduleRepository).findById(scheduleId);
    }

    @Test
    void listSchedulings_ReturnsPageOfSchedules() {
        // Arrange
        int page = 0;
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "dateSchedule"));
        Schedule schedule = new Schedule();
        Page<Schedule> schedulePage = new PageImpl<>(Collections.singletonList(schedule));

        when(scheduleRepository.findAll(pageRequest)).thenReturn(schedulePage);

        // Act
        Page<Schedule> result = scheduleService.listSchedulings(page, pageSize);

        // Assert
        assertEquals(1, result.getTotalElements());
        verify(scheduleRepository).findAll(pageRequest);
    }

    @Test
    void listSchedulingsByStatus_ReturnsPageOfSchedules() {
        // Arrange
        int page = 0;
        int pageSize = 10;
        Status status = Status.PENDENTE;
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "dateSchedule"));
        Schedule schedule = new Schedule();
        schedule.setStatus(status);
        Page<Schedule> schedulePage = new PageImpl<>(Collections.singletonList(schedule));

        when(scheduleRepository.findByStatus(status, pageRequest)).thenReturn(schedulePage);

        // Act
        Page<Schedule> result = scheduleService.listSchedulingsByStatus(status, page, pageSize);

        // Assert
        assertEquals(1, result.getTotalElements());
        verify(scheduleRepository).findByStatus(status, pageRequest);
    }
}
