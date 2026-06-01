package com.taskmanager.taskmanager.dto;

import com.taskmanager.taskmanager.entity.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Task.Priority priority;

    private Task.Status status;

    private LocalDateTime deadline;
}