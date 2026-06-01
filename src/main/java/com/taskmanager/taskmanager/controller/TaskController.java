package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.dto.AssignTaskRequest;
import com.taskmanager.taskmanager.dto.TaskRequest;
import com.taskmanager.taskmanager.dto.TaskResponse;
import com.taskmanager.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tasks", description = "Task CRUD and assignment")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request,
                                               Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(request, auth.getName()));
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getAll(Authentication auth, Pageable pageable) {
        return ResponseEntity.ok(taskService.getAllTasks(auth.getName(), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable Long id,
                                                Authentication auth) {
        return ResponseEntity.ok(taskService.getTaskById(id, auth.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody TaskRequest request,
                                               Authentication auth) {
        return ResponseEntity.ok(taskService.updateTask(id, request, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
        taskService.deleteTask(id, auth.getName());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<TaskResponse> assign(@PathVariable Long id,
                                               @Valid @RequestBody AssignTaskRequest request,
                                               Authentication auth) {
        return ResponseEntity.ok(taskService.assignTask(id, request, auth.getName()));
    }

    @GetMapping("/assigned-to-me")
    public ResponseEntity<List<TaskResponse>> assignedToMe(Authentication auth) {
        return ResponseEntity.ok(taskService.getAssignedToMe(auth.getName()));
    }
}