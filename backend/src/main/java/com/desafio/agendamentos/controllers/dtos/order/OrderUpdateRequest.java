package com.desafio.agendamentos.controllers.dtos.order;

import com.desafio.agendamentos.enums.PaymentType;
import com.desafio.agendamentos.services.validations.ValidPaymentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderUpdateRequest(
        @NotNull(message = "Payment type is mandatory")
        @ValidPaymentType
        PaymentType paymentType,

        @NotNull(message = "Total amount is mandatory")
        @Positive(message = "Total amount must be a positive number")
        Double totalAmount
) { }