package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.dto.AuthResponse;
import com.taskmanager.taskmanager.dto.LoginRequest;
import com.taskmanager.taskmanager.dto.RegisterRequest;

public interface UserService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}