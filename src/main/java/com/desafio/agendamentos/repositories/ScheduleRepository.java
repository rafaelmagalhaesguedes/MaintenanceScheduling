package com.desafio.agendamentos.repositories;

import com.desafio.agendamentos.entities.Schedule;
import com.desafio.agendamentos.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findScheduleByCustomerId(Long customerId);
    Page<Schedule> findByStatus(Status status, Pageable pageable);
}
