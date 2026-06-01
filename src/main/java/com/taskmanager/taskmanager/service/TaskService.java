package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.dto.AssignTaskRequest;
import com.taskmanager.taskmanager.dto.TaskRequest;
import com.taskmanager.taskmanager.dto.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskRequest request, String email);
    Page<TaskResponse> getAllTasks(String email, Pageable pageable);
    TaskResponse getTaskById(Long id, String email);
    TaskResponse updateTask(Long id, TaskRequest request, String email);
    void deleteTask(Long id, String email);
    TaskResponse assignTask(Long taskId, AssignTaskRequest request, String ownerEmail);
    List<TaskResponse> getAssignedToMe(String email);
}