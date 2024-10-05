package com.desafio.agendamentos.unit;

import com.desafio.agendamentos.entities.Mechanic;
import com.desafio.agendamentos.repositories.MechanicRepository;
import com.desafio.agendamentos.services.exceptions.MechanicNotFoundException;
import com.desafio.agendamentos.services.mechanic.MechanicServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.desafio.agendamentos.mocks.MechanicMocks.MECHANIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MechanicServiceTest {

    @Mock
    private MechanicRepository mechanicRepository;

    @InjectMocks
    private MechanicServiceImpl mechanicService;

    @Test
    public void createMechanic_WithValidData_ReturnsMechanic() {
        // Arrange
        when(mechanicRepository.save(any(Mechanic.class))).thenReturn(MECHANIC);

        // Act
        Mechanic result = mechanicService.create(MECHANIC);

        // Assert
        assertThat(result).isEqualTo(MECHANIC);
    }

    @Test
    public void findById_WithValidId_ReturnsMechanic() {
        // Arrange
        when(mechanicRepository.findById(MECHANIC.getId())).thenReturn(Optional.of(MECHANIC));

        // Act
        Mechanic result = mechanicService.findById(MECHANIC.getId());

        // Assert
        assertThat(result).isEqualTo(MECHANIC);
    }

    @Test
    public void findById_WithInvalidId_ThrowsMechanicNotFoundException() {
        // Arrange
        when(mechanicRepository.findById(MECHANIC.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> mechanicService.findById(MECHANIC.getId()))
                .isInstanceOf(MechanicNotFoundException.class);
    }

    @Test
    public void updateMechanic_WithValidData_UpdatesMechanic() {
        // Arrange
        when(mechanicRepository.findById(MECHANIC.getId())).thenReturn(Optional.of(MECHANIC));

        Mechanic updatedMechanic = Mechanic.builder()
                .id(MECHANIC.getId())
                .name("Updated Name")
                .email("updated@example.com")
                .numberPhone("31997745566")
                .specialty("Suspension")
                .register("123456")
                .build();

        // Act
        mechanicService.update(MECHANIC.getId(), updatedMechanic);

        // Assert
        verify(mechanicRepository, times(1)).save(any(Mechanic.class));
    }

    @Test
    public void deleteMechanic_WithValidId_DeletesMechanic() {
        // Arrange
        when(mechanicRepository.findById(MECHANIC.getId())).thenReturn(Optional.of(MECHANIC));

        // Act
        mechanicService.delete(MECHANIC.getId());

        // Assert
        verify(mechanicRepository, times(1)).delete(any(Mechanic.class));
    }

    @Test
    public void deleteMechanic_WithInvalidId_ThrowsMechanicNotFoundException() {
        // Arrange
        when(mechanicRepository.findById(MECHANIC.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> mechanicService.delete(MECHANIC.getId()))
                .isInstanceOf(MechanicNotFoundException.class);
    }
}