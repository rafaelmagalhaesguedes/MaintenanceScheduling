package com.desafio.agendamentos.services;

import static com.desafio.agendamentos.services.validations.SchedulingDateValidation.*;

import com.desafio.agendamentos.entities.Customer;
import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.Status;
import com.desafio.agendamentos.repositories.ScheduleRepository;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CustomerService customerService;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, CustomerService customerService) {
        this.scheduleRepository = scheduleRepository;
        this.customerService = customerService;
    }

    /**
     * Cria um novo agendamento.
     *
     * @param customerId ID do cliente.
     * @param schedule   Dados do agendamento.
     * @return Dados do agendamento criado.
     * @throws CustomerNotFoundException Se o cliente não for encontrado.
     */
    @Transactional
    public Schedule createSchedule(Long customerId, Schedule schedule) throws CustomerNotFoundException {
        // Verifica se o cliente existe
        Customer customer = customerService.findById(customerId);

        // Valida data do agendamento
        validateScheduleDate(schedule.getDateSchedule());

        // Formata data do agendamento
        LocalDateTime formattedDateSchedule = formatScheduleDate(schedule.getDateSchedule());

        // Seta os dados para criar um novo agendamento
        Schedule newSchedule = new Schedule();
        newSchedule.setCustomer(customer);
        newSchedule.setDescriptionService(schedule.getDescriptionService());
        newSchedule.setDateSchedule(formattedDateSchedule);
        newSchedule.setStatus(Status.PENDENTE);

        // Cria um novo agendamento e retorna os dados do agendamento criado
        return scheduleRepository.save(newSchedule);
    }

    /**
     * Formata a data do agendamento.
     *
     * @param dateSchedule Data do agendamento.
     * @return Data do agendamento formatada.
     */
    private static LocalDateTime formatScheduleDate(LocalDateTime dateSchedule) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(dateSchedule.format(formatter), formatter);
    }
}
