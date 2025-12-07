<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - TODO App</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css" />
    <link rel="stylesheet" href="css/auth.css" />
</head>
<body>
    <div class="auth-container">
        <div class="text-center mb-4">
            <div class="logo">[ TODO ]</div>
            <p class="auth-subtitle">Sign in to manage your tasks</p>
        </div>

        <c:if test="${not empty error}">
            <div class="auth-alert auth-alert-danger">${error}</div>
        </c:if>
        
        <c:if test="${not empty message}">
            <div class="auth-alert auth-alert-success">${message}</div>
        </c:if>
        
        <c:if test="${param.registered != null}">
            <div class="auth-alert auth-alert-success">Registration successful! Please login.</div>
        </c:if>

        <form action="/api/v1/auth/login" method="post">
            <div class="auth-form-group">
                <label class="auth-label" for="username">Username</label>
                <input type="text" class="auth-input" id="username" name="username" 
                       placeholder="Enter your username" required autocomplete="username">
            </div>
            
            <div class="auth-form-group">
                <label class="auth-label" for="password">Password</label>
                <input type="password" class="auth-input" id="password" name="password" 
                       placeholder="Enter your password" required autocomplete="current-password">
            </div>
            
            <button type="submit" class="btn-auth">Sign In</button>
        </form>

        <div class="auth-link">
            <p>Don't have an account? <a href="/signup">Create one</a></p>
        </div>
    </div>
</body>
</html>
