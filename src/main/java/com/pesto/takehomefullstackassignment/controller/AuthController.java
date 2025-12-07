package com.pesto.takehomefullstackassignment.controller;

import com.pesto.takehomefullstackassignment.model.SignupRequest;
import com.pesto.takehomefullstackassignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String email,
                         @RequestParam String password,
                         Model model) {
        // Basic validation
        if (username == null || username.trim().length() < 3) {
            model.addAttribute("error", "Username must be at least 3 characters");
            return "signup";
        }
        if (email == null || !email.contains("@")) {
            model.addAttribute("error", "Please enter a valid email");
            return "signup";
        }
        if (password == null || password.length() < 8) {
            model.addAttribute("error", "Password must be at least 8 characters");
            return "signup";
        }

        try {
            SignupRequest signupRequest = new SignupRequest();
            signupRequest.setUsername(username.trim());
            signupRequest.setEmail(email.trim());
            signupRequest.setPassword(password);
            userService.registerUser(signupRequest);
            return "redirect:/login?registered=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }
}
