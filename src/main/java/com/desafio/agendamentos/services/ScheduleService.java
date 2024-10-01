package com.desafio.agendamentos.services;

import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.Status;
import com.desafio.agendamentos.repositories.ScheduleRepository;
import com.desafio.agendamentos.services.exceptions.ScheduleNotFoundException;
import com.desafio.agendamentos.services.exceptions.StatusValidateException;
import com.desafio.agendamentos.services.validations.StatusValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.desafio.agendamentos.services.validations.StatusValidation.validateStatus;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * Busca um agendamento por ID.
     *
     * @param scheduleId Id do agendamento.
     * @return dados do agendamento encontrado.
     */
    public Schedule findScheduleById(Long scheduleId) throws ScheduleNotFoundException {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(ScheduleNotFoundException::new);
    }

    /**
     * Atualiza um agendamento como realizado.
     *
     * @param scheduleId Id do agendamento.
     * @return agendamento com status atualizado.
     */
    public Schedule updateScheduleStatus(Long scheduleId) throws ScheduleNotFoundException {
        var scheduleFromDb = findScheduleById(scheduleId);

        // Atualiza status para serviço REALIZADO
        scheduleFromDb.setStatus(Status.REALIZADO);

        return scheduleRepository.save(scheduleFromDb);
    }

    /**
     * Lista todos os agendamentos com paginação, ordenados por data ascendente.
     *
     * @param page     Número da página.
     * @param pageSize Tamanho da página.
     * @return Página de agendamentos.
     */
    public Page<Schedule> listSchedulings(int page, int pageSize) {
        var sort = Sort.by(Sort.Direction.ASC, "dateSchedule");
        var pageRequest = PageRequest.of(page, pageSize, sort);

        return scheduleRepository.findAll(pageRequest);
    }

    /**
     * Lista agendamentos filtrados por Status com paginação.
     *
     * @param status   Status do agendamento.
     * @param page     Número da página.
     * @param pageSize Tamanho da página.
     * @return Página de agendamentos filtrados por status.
     */
    public Page<Schedule> listSchedulingsByStatus(Status status, int page, int pageSize) {
        var sort = Sort.by(Sort.Direction.ASC, "dateSchedule");
        var statusToUppercase = Status.valueOf(status.name().toUpperCase());
        var pageRequest = PageRequest.of(page, pageSize, sort);

        return scheduleRepository.findByStatus(statusToUppercase, pageRequest);
    }
}