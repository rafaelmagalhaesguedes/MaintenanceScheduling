package com.desafio.agendamentos.advice;

import com.desafio.agendamentos.services.exceptions.CustomerExistsException;
import com.desafio.agendamentos.services.exceptions.NotFoundException;
import com.desafio.agendamentos.services.exceptions.SchedulingDateException;
import com.desafio.agendamentos.services.exceptions.VehicleValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador global para tratamento de exceções.
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     * Trata as exceções de validação dos dados de solicitações HTTP request.
     *
     * @param ex      Exceção lançada quando a validação falha.
     * @param request Objeto de solicitação web.
     * @return Resposta HTTP com detalhes dos erros de validação.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> body = new HashMap<>();
        body.put("failures", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Trata exceções de cliente já existente.
     *
     * @param ex Exceção lançada quando o cliente já existe.
     * @return Resposta HTTP com mensagem de conflito.
     */
    @ExceptionHandler(CustomerExistsException.class)
    public ResponseEntity<String> handleCustomerExists(CustomerExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    /**
     * Trata exceções de objetos não encontrados.
     *
     * @param ex Exceção lançada quando o objeto não é encontrado.
     * @return Resposta HTTP com mensagem de não encontrado.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    /**
     * Trata exceções de data de agendamento inválida.
     *
     * @param ex Exceção lançada quando a data de agendamento é inválida.
     * @return Resposta HTTP com mensagem de entidade não processável.
     */
    @ExceptionHandler(SchedulingDateException.class)
    public ResponseEntity<String> handleSchedulingDate(SchedulingDateException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ex.getMessage());
    }

    /**
     * Trata exceções de validação na criação de um veículo
     *
     * @param ex Exceção lançada quando os dados de criação são inválidos.
     * @return Resposta HTTP com mensagem de entidade não processável.
     */
    @ExceptionHandler(VehicleValidateException.class)
    public ResponseEntity<String> handleVehicleValidate(VehicleValidateException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ex.getMessage());
    }
}