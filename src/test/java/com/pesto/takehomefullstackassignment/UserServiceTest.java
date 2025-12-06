package com.pesto.takehomefullstackassignment;

import com.pesto.takehomefullstackassignment.entity.User;
import com.pesto.takehomefullstackassignment.model.SignupRequest;
import com.pesto.takehomefullstackassignment.repository.UserRepository;
import com.pesto.takehomefullstackassignment.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private SignupRequest signupRequest;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest();
        signupRequest.setUsername("testuser");
        signupRequest.setEmail("test@example.com");
        signupRequest.setPassword("password123");
    }

    @Test
    void registerUser_Success() {
        User user = userService.registerUser(signupRequest);

        assertNotNull(user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertTrue(passwordEncoder.matches("password123", user.getPassword()));
    }

    @Test
    void registerUser_PasswordIsEncrypted() {
        User user = userService.registerUser(signupRequest);

        assertNotEquals("password123", user.getPassword());
        assertTrue(user.getPassword().startsWith("$2a$"));
    }

    @Test
    void registerUser_DuplicateUsername_ThrowsException() {
        userService.registerUser(signupRequest);

        SignupRequest duplicateRequest = new SignupRequest();
        duplicateRequest.setUsername("testuser");
        duplicateRequest.setEmail("different@example.com");
        duplicateRequest.setPassword("password123");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.registerUser(duplicateRequest)
        );
        assertEquals("Username is already in use", exception.getMessage());
    }

    @Test
    void registerUser_DuplicateEmail_ThrowsException() {
        userService.registerUser(signupRequest);

        SignupRequest duplicateRequest = new SignupRequest();
        duplicateRequest.setUsername("differentuser");
        duplicateRequest.setEmail("test@example.com");
        duplicateRequest.setPassword("password123");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.registerUser(duplicateRequest)
        );
        assertEquals("Email is already in use", exception.getMessage());
    }

    @Test
    void findByUsername_UserExists() {
        userService.registerUser(signupRequest);

        var foundUser = userService.findByUsername("testuser");

        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    void findByUsername_UserNotExists() {
        var foundUser = userService.findByUsername("nonexistent");

        assertFalse(foundUser.isPresent());
    }

    @Test
    void existsByUsername_ReturnsTrueWhenExists() {
        userService.registerUser(signupRequest);

        assertTrue(userService.existsByUsername("testuser"));
    }

    @Test
    void existsByUsername_ReturnsFalseWhenNotExists() {
        assertFalse(userService.existsByUsername("nonexistent"));
    }

    @Test
    void existsByEmail_ReturnsTrueWhenExists() {
        userService.registerUser(signupRequest);

        assertTrue(userService.existsByEmail("test@example.com"));
    }

    @Test
    void existsByEmail_ReturnsFalseWhenNotExists() {
        assertFalse(userService.existsByEmail("nonexistent@example.com"));
    }
}

