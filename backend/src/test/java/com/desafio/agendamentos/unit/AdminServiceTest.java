package com.desafio.agendamentos.unit;

import com.desafio.agendamentos.repositories.AdminRepository;
import com.desafio.agendamentos.repositories.UserRepository;
import com.desafio.agendamentos.services.admin.AdminServiceImpl;
import com.desafio.agendamentos.services.exceptions.UserExistsException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import static com.desafio.agendamentos.mocks.AdminMocks.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    public void createAdmin_WithValidData_SavesAdmin() {
        // Arrange
        when(userRepository.existsByEmail(ADMIN.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(ADMIN.getPassword())).thenReturn("encodedPassword");

        // Act
        adminService.create(ADMIN);

        // Assert
        verify(adminRepository, times(1))
                .save(argThat(admin -> admin.getEmail().equals(ADMIN.getEmail()) && admin.getPassword().equals("encodedPassword")
        ));
    }

    @Test
    public void createAdmin_WithExistingEmail_ThrowsUserExistsException() {
        // Arrange
        when(userRepository.existsByEmail(ADMIN.getEmail())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> adminService.create(ADMIN))
                .isInstanceOf(UserExistsException.class);
    }
}
