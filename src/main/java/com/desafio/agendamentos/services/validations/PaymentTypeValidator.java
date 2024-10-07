package com.desafio.agendamentos.services.validations;

import com.desafio.agendamentos.enums.PaymentType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PaymentTypeValidator implements ConstraintValidator<ValidPaymentType, PaymentType> {

    @Override
    public void initialize(ValidPaymentType constraintAnnotation) {
    }

    @Override
    public boolean isValid(PaymentType paymentType, ConstraintValidatorContext context) {
        if (paymentType == null) {
            return false;
        }

        // Verifica se o valor é um dos valores válidos do enum PaymentType
        for (PaymentType type : PaymentType.values()) {
            if (type == paymentType) {
                return true;
            }
        }
        return false;
    }
}
