package com.desafio.agendamentos.services.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação personalizada para validar se um campo é um valor válido do enum PaymentType.
 */
@Constraint(validatedBy = PaymentTypeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPaymentType {
    /**
     * Mensagem de erro padrão que será usada se a validação falhar.
     *
     * @return a mensagem de erro padrão
     */
    String message() default "Invalid payment type";

    /**
     * Grupos de validação que podem ser usados para aplicar diferentes regras de validação em diferentes contextos.
     *
     * @return os grupos de validação
     */
    Class<?>[] groups() default {};

    /**
     * Payloads que podem ser usados pelos clientes da API de validação para fornecer informações adicionais sobre a falha de validação.
     *
     * @return os payloads
     */
    Class<? extends Payload>[] payload() default {};
}