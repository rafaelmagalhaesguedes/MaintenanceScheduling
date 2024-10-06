package com.desafio.agendamentos.services.mechanic;

import com.desafio.agendamentos.entities.Mechanic;
import com.desafio.agendamentos.repositories.MechanicRepository;
import com.desafio.agendamentos.services.exceptions.MechanicNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementação da interface IMechanicService.
 * Fornece métodos para criar, encontrar, atualizar e deletar mecânicos.
 */
@Service
public class MechanicServiceImpl implements IMechanicService {

    private final MechanicRepository mechanicRepository;

    @Autowired
    public MechanicServiceImpl(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

    /**
     * Cria um novo mecânico.
     *
     * @param mechanic o mecânico a ser criado
     * @return o mecânico criado
     */
    @Override
    @Transactional
    public Mechanic create(Mechanic mechanic) {
        return mechanicRepository.save(mechanic);
    }

    /**
     * Encontra um mecânico pelo ID.
     *
     * @param mechanicId o ID do mecânico
     * @return o mecânico encontrado
     * @throws MechanicNotFoundException se o mecânico não for encontrado
     */
    @Override
    public Mechanic findById(Long mechanicId) throws MechanicNotFoundException {
        return mechanicRepository.findById(mechanicId)
                .orElseThrow(MechanicNotFoundException::new);
    }

    /**
     * Atualiza um mecânico existente.
     *
     * @param mechanicId o ID do mecânico
     * @param mechanic os detalhes do mecânico a serem atualizados
     * @throws MechanicNotFoundException se o mecânico não for encontrado
     */
    @Override
    @Transactional
    public void update(Long mechanicId, Mechanic mechanic) throws MechanicNotFoundException {
        Mechanic existingMechanic = findById(mechanicId);

        existingMechanic.setName(mechanic.getName());
        existingMechanic.setEmail(mechanic.getEmail());
        existingMechanic.setNumberPhone(mechanic.getNumberPhone());
        existingMechanic.setSpecialty(mechanic.getSpecialty());
        existingMechanic.setRegister(mechanic.getRegister());

        mechanicRepository.save(existingMechanic);
    }

    /**
     * Encontra todos os mecânicos.
     *
     * @return uma lista de todos os mecânicos
     */
    @Override
    public List<Mechanic> findAll() {
        return mechanicRepository.findAll();
    }

    /**
     * Deleta um mecânico pelo ID.
     *
     * @param mechanicId o ID do mecânico
     * @throws MechanicNotFoundException se o mecânico não for encontrado
     */
    @Override
    @Transactional
    public void delete(Long mechanicId) throws MechanicNotFoundException {
        Mechanic mechanic = findById(mechanicId);
        mechanicRepository.delete(mechanic);
    }
}
