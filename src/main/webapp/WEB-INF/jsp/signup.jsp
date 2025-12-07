<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up - TODO App</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css" />
    <link rel="stylesheet" href="css/auth.css" />
</head>
<body>
    <div class="auth-container">
        <div class="text-center mb-4">
            <div class="logo">[ TODO ]</div>
            <p class="auth-subtitle">Create your account to get started</p>
        </div>

        <c:if test="${not empty error}">
            <div class="auth-alert auth-alert-danger">${error}</div>
        </c:if>

        <form action="/signup" method="post">
            <div class="auth-form-group">
                <label class="auth-label" for="username">Username</label>
                <input type="text" class="auth-input" id="username" name="username"
                       placeholder="Choose a username" required autocomplete="username"
                       minlength="3" maxlength="50">
            </div>
            
            <div class="auth-form-group">
                <label class="auth-label" for="email">Email</label>
                <input type="email" class="auth-input" id="email" name="email"
                       placeholder="Enter your email" required autocomplete="email">
            </div>
            
            <div class="auth-form-group">
                <label class="auth-label" for="password">Password</label>
                <input type="password" class="auth-input" id="password" name="password"
                       placeholder="Create a password" required autocomplete="new-password"
                       minlength="6">
                <p class="form-hint">Must be at least 6 characters</p>
            </div>
            
            <button type="submit" class="btn-auth">Create Account</button>
        </form>

        <div class="auth-link">
            <p>Already have an account? <a href="/login">Sign in</a></p>
        </div>
    </div>
</body>
</html>
