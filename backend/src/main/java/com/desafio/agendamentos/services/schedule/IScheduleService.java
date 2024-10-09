package com.desafio.agendamentos.services.schedule;

import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.ScheduleStatus;
import com.desafio.agendamentos.services.exceptions.*;

import java.util.List;

/**
 * Interface para o serviço de agendamento.
 * Define métodos para criar, encontrar e cancelar agendamentos.
 */
public interface IScheduleService {

    /**
     * Cria um novo agendamento para um cliente.
     *
     * @param customerId o ID do cliente
     * @param schedule o agendamento a ser criado
     * @return o agendamento criado
     * @throws CustomerNotFoundException se o cliente não for encontrado
     * @throws VehicleNotFoundException se o cliente não tiver veículos
     */
    Schedule createSchedule(Long customerId, Schedule schedule) throws CustomerNotFoundException, VehicleNotFoundException;

    /**
     * Encontra agendamentos pelo ID do cliente.
     *
     * @param customerId o ID do cliente
     * @return uma lista de agendamentos para o cliente
     * @throws ScheduleNotFoundException se nenhum agendamento for encontrado para o cliente
     */
    List<Schedule> findSchedulesByCustomerId(Long customerId) throws ScheduleNotFoundException;

    /**
     * Cancela um agendamento para um cliente.
     *
     * @param customerId o ID do cliente
     * @param scheduleId o ID do agendamento
     * @return o agendamento cancelado
     * @throws StatusValidateException se o scheduleStatus for inválido para cancelamento
     * @throws CustomerNotFoundException se o cliente não for encontrado
     * @throws ScheduleNotFoundException se o agendamento não for encontrado
     */
    Schedule cancelSchedule(Long customerId, Long scheduleId) throws StatusValidateException, CustomerNotFoundException, ScheduleNotFoundException;

    /**
     * Encontra todos os agendamentos com paginação.
     *
     * @param pageNumber o número da página a ser recuperada
     * @param pageSize o número de itens por página
     * @return uma lista de agendamentos
     * @throws ScheduleNotFoundException se nenhum agendamento for encontrado
     */
    List<Schedule> findAllSchedules(int pageNumber, int pageSize) throws ScheduleNotFoundException;
}