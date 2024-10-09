package com.desafio.agendamentos.services.schedule;

import static com.desafio.agendamentos.services.validations.schedule.ScheduleValidation.validateScheduleDate;

import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.ScheduleStatus;
import com.desafio.agendamentos.repositories.ScheduleRepository;
import com.desafio.agendamentos.services.customer.ICustomerService;
import com.desafio.agendamentos.services.exceptions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        // Verifica se o cliente possui algum veículo cadastrado
        if (customer.getVehicles().isEmpty()) {
            throw new VehicleNotFoundException();
        }

        // Associa o veículo para a manutenção
        var vehicleId = schedule.getVehicle().getId();
        var vehicleFromDb = customer.getVehicles().stream()
                .filter(v -> v.getId().equals(vehicleId))
                .findFirst()
                .orElseThrow(VehicleNotFoundException::new);

        // Valida a data do agendamento
        validateScheduleDate(schedule.getDateSchedule());

        // Formata a data
        var dateSchedule = formatScheduleDate(schedule.getDateSchedule());

        // Cria novo agendamento com scheduleStatus PENDENTE
        var newSchedule = Schedule.builder()
                .customer(customer)
                .descriptionService(schedule.getDescriptionService())
                .dateSchedule(dateSchedule)
                .vehicle(vehicleFromDb)
                .scheduleStatus(ScheduleStatus.PENDENTE)
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
     * @return o agendamento cancelado
     * @throws StatusValidateException se o scheduleStatus for inválido para cancelamento
     * @throws CustomerNotFoundException se o cliente não for encontrado
     * @throws ScheduleNotFoundException se o agendamento não for encontrado
     */
    @Override
    @Transactional
    public Schedule cancelSchedule(Long customerId, Long scheduleId) throws StatusValidateException, CustomerNotFoundException, ScheduleNotFoundException {

        // Carrega os dados do agendamento
        var scheduleFromDb = scheduleRepository.findById(scheduleId)
                .orElseThrow(ScheduleNotFoundException::new);

        // Verifica se o id do customer bate com a do agendamento
        if (!scheduleFromDb.getCustomer().getId().equals(customerId)) {
            throw new StatusValidateException("Agendamento não encontrado para o cliente com id: " + customerId);
        }

        // Verifica o scheduleStatus está como CANCELADO
        if (scheduleFromDb.getScheduleStatus() != ScheduleStatus.CANCELADO) {
            throw new StatusValidateException("ScheduleStatus inválido para cancelamento");
        }

        // Atualiza o scheduleStatus
        scheduleFromDb.setScheduleStatus(ScheduleStatus.CANCELADO);

        // Salva tabela atualizada
        scheduleRepository.save(scheduleFromDb);

        return scheduleFromDb;
    }

    /**
     * Encontra todos os agendamentos com paginação.
     *
     * @param pageNumber o número da página a ser recuperada
     * @param pageSize   o número de itens por página
     * @return uma lista de agendamentos
     * @throws ScheduleNotFoundException se nenhum agendamento for encontrado
     */
    @Override
    public List<Schedule> findAllSchedules(int pageNumber, int pageSize) throws ScheduleNotFoundException {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Schedule> page = scheduleRepository.findAll(pageable);

        return page.getContent();
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