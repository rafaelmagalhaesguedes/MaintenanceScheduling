package com.desafio.agendamentos.services.schedule;

import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.Status;
import com.desafio.agendamentos.repositories.ScheduleRepository;
import com.desafio.agendamentos.services.customer.ICustomerService;
import com.desafio.agendamentos.services.exceptions.*;
import com.desafio.agendamentos.services.validations.schedule.ScheduleValidation;
import com.desafio.agendamentos.services.validations.status.StatusValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Implementação da interface IScheduleService.
 * Fornece métodos para criar, encontrar e cancelar agendamentos.
 */
@Service
public class ScheduleServiceImpl implements IScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ICustomerService customerService;

    /**
     * Constrói um ScheduleServiceImpl com o ScheduleRepository e ICustomerService especificados.
     *
     * @param scheduleRepository o ScheduleRepository a ser usado
     * @param customerService o ICustomerService a ser usado
     */
    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ICustomerService customerService) {
        this.scheduleRepository = scheduleRepository;
        this.customerService = customerService;
    }

    /**
     * Cria um novo agendamento para um cliente.
     *
     * @param customerId o ID do cliente
     * @param schedule o agendamento a ser criado
     * @return o agendamento criado
     * @throws CustomerNotFoundException se o cliente não for encontrado
     * @throws VehicleNotFoundException se o cliente não tiver veículos
     */
    @Override
    @Transactional
    public Schedule createSchedule(Long customerId, Schedule schedule) throws CustomerNotFoundException, VehicleNotFoundException {
        var customer = customerService.findCustomerById(customerId);

        if (customer.getVehicles().isEmpty()) {
            throw new VehicleNotFoundException();
        }

        ScheduleValidation.validateScheduleDate(schedule.getDateSchedule());

        var dateSchedule = formatScheduleDate(schedule.getDateSchedule());

        var newSchedule = Schedule.builder()
                .customer(customer)
                .descriptionService(schedule.getDescriptionService())
                .dateSchedule(dateSchedule)
                .status(Status.PENDENTE)
                .build();

        return scheduleRepository.save(newSchedule);
    }

    /**
     * Encontra agendamentos pelo ID do cliente.
     *
     * @param customerId o ID do cliente
     * @return uma lista de agendamentos para o cliente
     * @throws ScheduleNotFoundException se nenhum agendamento for encontrado para o cliente
     */
    @Override
    public List<Schedule> findSchedulesByCustomerId(Long customerId) throws ScheduleNotFoundException {
        return scheduleRepository.findScheduleByCustomerId(customerId)
                .stream()
                .toList();
    }

    /**
     * Cancela um agendamento para um cliente.
     *
     * @param customerId o ID do cliente
     * @param scheduleId o ID do agendamento
     * @param status o status a ser definido para o agendamento
     * @return o agendamento cancelado
     * @throws StatusValidateException se o status for inválido para cancelamento
     * @throws CustomerNotFoundException se o cliente não for encontrado
     * @throws ScheduleNotFoundException se o agendamento não for encontrado
     */
    @Override
    @Transactional
    public Schedule cancelSchedule(Long customerId, Long scheduleId, Status status) throws StatusValidateException, CustomerNotFoundException, ScheduleNotFoundException {
        var scheduleFromDb = scheduleRepository.findById(scheduleId)
                .orElseThrow(ScheduleNotFoundException::new);

        StatusValidation.validateStatus(status);

        if (!scheduleFromDb.getCustomer().getId().equals(customerId)) {
            throw new StatusValidateException("Agendamento não encontrado para o cliente com id: " + customerId);
        }

        if (status != Status.CANCELADO) {
            throw new StatusValidateException("Status inválido para cancelamento");
        }

        scheduleFromDb.setStatus(Status.CANCELADO);

        scheduleRepository.save(scheduleFromDb);

        return scheduleFromDb;
    }

    /**
     * Formata a data do agendamento para um padrão específico.
     *
     * @param dateSchedule a data a ser formatada
     * @return a data formatada
     */
    private static LocalDateTime formatScheduleDate(LocalDateTime dateSchedule) {
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return LocalDateTime.parse(dateSchedule.format(formatter), formatter);
    }
}