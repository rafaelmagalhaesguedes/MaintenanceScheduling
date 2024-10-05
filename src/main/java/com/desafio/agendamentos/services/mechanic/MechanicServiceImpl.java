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

    @Override
    @Transactional
    public Mechanic create(Mechanic mechanic) {
        return mechanicRepository.save(mechanic);
    }

    @Override
    public Mechanic findById(Long mechanicId) throws MechanicNotFoundException {
        return mechanicRepository.findById(mechanicId)
                .orElseThrow(MechanicNotFoundException::new);
    }

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

    @Override
    public List<Mechanic> findAll() {
        return mechanicRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long mechanicId) throws MechanicNotFoundException {
        Mechanic mechanic = findById(mechanicId);
        mechanicRepository.delete(mechanic);
    }
}
