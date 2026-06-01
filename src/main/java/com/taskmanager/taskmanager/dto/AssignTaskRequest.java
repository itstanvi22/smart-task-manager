package com.taskmanager.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssignTaskRequest {

    @NotBlank
    private String assigneeEmail;
}