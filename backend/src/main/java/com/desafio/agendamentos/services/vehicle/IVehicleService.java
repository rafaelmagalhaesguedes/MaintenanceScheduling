package com.desafio.agendamentos.services.vehicle;

import com.desafio.agendamentos.entities.Vehicle;
import com.desafio.agendamentos.services.exceptions.CustomerNotFoundException;
import com.desafio.agendamentos.services.exceptions.VehicleNotFoundException;
import com.desafio.agendamentos.services.exceptions.VehicleValidateException;

import java.util.List;

/**
 * Interface para o serviço de veículos.
 * Define métodos para criar, encontrar, atualizar e deletar veículos.
 */
public interface IVehicleService {

    /**
     * Cria um novo veículo para um cliente.
     *
     * @param customerId o ID do cliente
     * @param vehicle o veículo a ser criado
     * @return o veículo criado
     * @throws CustomerNotFoundException se o cliente não for encontrado
     * @throws VehicleValidateException se a validação do veículo falhar
     */
    Vehicle createVehicle(Long customerId, Vehicle vehicle) throws CustomerNotFoundException, VehicleValidateException;

    /**
     * Encontra veículos pelo ID do cliente.
     *
     * @param customerId o ID do cliente
     * @return uma lista de veículos para o cliente
     * @throws VehicleNotFoundException se nenhum veículo for encontrado para o cliente
     */
    List<Vehicle> findVehiclesByCustomerId(Long customerId) throws VehicleNotFoundException;

    /**
     * Atualiza um veículo para um cliente.
     *
     * @param customerId o ID do cliente
     * @param vehicleId o ID do veículo
     * @param vehicleDetails os detalhes do veículo a serem atualizados
     * @return o veículo atualizado
     * @throws VehicleNotFoundException se o veículo não for encontrado
     * @throws CustomerNotFoundException se o cliente não for encontrado
     */
    Vehicle updateVehicle(Long customerId, Long vehicleId, Vehicle vehicleDetails) throws VehicleNotFoundException, CustomerNotFoundException;

    /**
     * Deleta um veículo para um cliente.
     *
     * @param customerId o ID do cliente
     * @param vehicleId o ID do veículo
     * @return o veículo deletado
     * @throws VehicleNotFoundException se o veículo não for encontrado
     * @throws CustomerNotFoundException se o cliente não for encontrado
     */
    Vehicle deleteVehicle(Long customerId, Long vehicleId) throws VehicleNotFoundException, CustomerNotFoundException;
}