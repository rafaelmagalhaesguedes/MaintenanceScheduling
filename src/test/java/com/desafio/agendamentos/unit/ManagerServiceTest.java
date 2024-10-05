package com.desafio.agendamentos.unit;

import com.desafio.agendamentos.entities.Manager;
import com.desafio.agendamentos.repositories.ManagerRepository;
import com.desafio.agendamentos.repositories.UserRepository;
import com.desafio.agendamentos.services.exceptions.ManagerNotFoundException;
import com.desafio.agendamentos.services.exceptions.UserExistsException;
import com.desafio.agendamentos.services.manager.ManagerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.desafio.agendamentos.mocks.ManagerMocks.MANAGER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ManagerServiceImpl managerService;

    @Test
    public void createManager_WithValidData_ReturnsManager() {
        // Arrange
        when(userRepository.existsByEmail(MANAGER.getEmail()))
                .thenReturn(false);

        when(passwordEncoder.encode(MANAGER.getPassword()))
                .thenReturn("encodedPassword");

        when(managerRepository.save(any(Manager.class)))
                .thenReturn(MANAGER);

        // Act
        Manager result = managerService.create(MANAGER);

        // Assert
        assertThat(result).isEqualTo(MANAGER);
        assertThat(result.getPassword()).isEqualTo("encodedPassword");
    }

    @Test
    public void createManager_WithExistingEmail_ThrowsExistingUserException() {
        // Arrange
        when(userRepository.existsByEmail(MANAGER.getEmail()))
                .thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> managerService.create(MANAGER))
                .isInstanceOf(UserExistsException.class);
    }

    @Test
    public void findById_WithValidId_ReturnsManager() {
        // Arrange
        when(managerRepository.findById(MANAGER.getId()))
                .thenReturn(Optional.of(MANAGER));

        // Act
        Manager result = managerService.findById(MANAGER.getId());

        // Assert
        assertThat(result).isEqualTo(MANAGER);
    }

    @Test
    public void findById_WithInvalidId_ThrowsManagerNotFoundException() {
        // Arrange
        when(managerRepository.findById(MANAGER.getId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> managerService.findById(MANAGER.getId()))
                .isInstanceOf(ManagerNotFoundException.class);
    }

    @Test
    public void updateManager_WithValidData_UpdatesManager() {
        // Arrange
        when(managerRepository.findById(MANAGER.getId()))
                .thenReturn(Optional.of(MANAGER));

        Manager updatedManager = Manager.managerBuilder()
                .id(MANAGER.getId())
                .name("Updated Name")
                .email("updated@example.com")
                .register(MANAGER.getRegister())
                .department(MANAGER.getDepartment())
                .location(MANAGER.getLocation())
                .build();

        // Act
        managerService.update(MANAGER.getId(), updatedManager);

        // Assert
        verify(managerRepository, times(1))
                .save(any(Manager.class));
    }

    @Test
    public void updateActiveStatus_WithValidId_UpdatesStatus() {
        // Arrange
        when(managerRepository.findById(MANAGER.getId()))
                .thenReturn(Optional.of(MANAGER));

        // Act
        managerService.updateActiveStatus(MANAGER.getId(), false);

        // Assert
        verify(managerRepository, times(1))
                .save(any(Manager.class));

        assertThat(MANAGER.getIsActive()).isFalse();
    }

    @Test
    public void softDelete_WithValidId_SoftDeletesManager() {
        // Arrange
        when(managerRepository.findById(MANAGER.getId()))
                .thenReturn(Optional.of(MANAGER));

        // Act
        managerService.softDelete(MANAGER.getId());

        // Assert
        verify(managerRepository, times(1))
                .save(any(Manager.class));

        assertThat(MANAGER.getIsDeleted()).isTrue();
    }
}
