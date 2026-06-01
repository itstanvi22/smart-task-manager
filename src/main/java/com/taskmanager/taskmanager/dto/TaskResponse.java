package com.taskmanager.taskmanager.dto;

import com.taskmanager.taskmanager.entity.Task;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Task.Priority priority;
    private Task.Status status;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
    private String ownerUsername;
    private String assigneeUsername;

    public static TaskResponse from(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(task.getStatus())
                .deadline(task.getDeadline())
                .createdAt(task.getCreatedAt())
                .ownerUsername(task.getOwner().getUsername())
                .assigneeUsername(task.getAssignee() != null ? task.getAssignee().getUsername() : null)
                .build();
    }
}