package com.desafio.agendamentos.repositories;

import com.desafio.agendamentos.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
