package com.pesto.takehomefullstackassignment;

import com.pesto.takehomefullstackassignment.entity.User;
import com.pesto.takehomefullstackassignment.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void loginPage_ReturnsLoginView() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void loginPage_WithError_ShowsErrorMessage() throws Exception {
        mockMvc.perform(get("/login").param("error", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void loginPage_WithLogout_ShowsLogoutMessage() throws Exception {
        mockMvc.perform(get("/login").param("logout", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    void signupPage_ReturnsSignupView() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }

    @Test
    void signup_ValidRequest_CreatesUserAndRedirects() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("username", "newuser")
                        .param("email", "new@example.com")
                        .param("password", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?registered=true"));

        assertTrue(userRepository.existsByUsername("newuser"));
        assertTrue(userRepository.existsByEmail("new@example.com"));
    }

    @Test
    void signup_DuplicateUsername_ShowsError() throws Exception {
        User existingUser = new User("existinguser", "existing@example.com", passwordEncoder.encode("password"));
        userRepository.save(existingUser);

        mockMvc.perform(post("/signup")
                        .param("username", "existinguser")
                        .param("email", "new@example.com")
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void signup_DuplicateEmail_ShowsError() throws Exception {
        User existingUser = new User("existinguser", "existing@example.com", passwordEncoder.encode("password"));
        userRepository.save(existingUser);

        mockMvc.perform(post("/signup")
                        .param("username", "newuser")
                        .param("email", "existing@example.com")
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void signup_InvalidEmail_ShowsValidationError() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("username", "newuser")
                        .param("email", "invalid-email")
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }

    @Test
    void signup_ShortPassword_ShowsValidationError() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("username", "newuser")
                        .param("email", "new@example.com")
                        .param("password", "12345"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }

    @Test
    void login_ValidCredentials_AuthenticatesAndRedirects() throws Exception {
        User user = new User("loginuser", "login@example.com", passwordEncoder.encode("password123"));
        userRepository.save(user);

        mockMvc.perform(post("/api/v1/auth/login")
                        .param("username", "loginuser")
                        .param("password", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void login_InvalidCredentials_RedirectsToLoginWithError() throws Exception {
        User user = new User("loginuser", "login@example.com", passwordEncoder.encode("password123"));
        userRepository.save(user);

        mockMvc.perform(post("/api/v1/auth/login")
                        .param("username", "loginuser")
                        .param("password", "wrongpassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error=true"));
    }

    @Test
    void accessProtectedResource_Unauthenticated_RedirectsToLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());
    }
}
