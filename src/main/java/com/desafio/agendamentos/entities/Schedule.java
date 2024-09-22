package com.desafio.agendamentos.entities;

import com.desafio.agendamentos.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private LocalDateTime dateSchedule;

    private String descriptionService;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Schedule(LocalDateTime dateSchedule, String descriptionService) {
        this.dateSchedule = dateSchedule;
        this.descriptionService = descriptionService;
    }
}