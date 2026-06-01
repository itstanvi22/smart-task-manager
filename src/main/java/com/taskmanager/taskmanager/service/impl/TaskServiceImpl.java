package com.taskmanager.taskmanager.service.impl;

import com.taskmanager.taskmanager.dto.TaskRequest;
import com.taskmanager.taskmanager.dto.TaskResponse;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.repository.UserRepository;
import com.taskmanager.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import com.taskmanager.taskmanager.dto.AssignTaskRequest;
import com.taskmanager.taskmanager.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }



    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest request, String email) {
        User owner = getUser(email);
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .status(request.getStatus() != null ? request.getStatus() : Task.Status.TODO)
                .deadline(request.getDeadline())
                .owner(owner)
                .build();
        return TaskResponse.from(taskRepository.save(task));
    }
    
    @Override
    @Transactional
    public TaskResponse assignTask(Long taskId, AssignTaskRequest request, String ownerEmail) {
    User owner = getUser(ownerEmail);

    Task task = taskRepository.findByIdAndOwner(taskId, owner)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Task not found or you don't have permission"));

    User assignee = userRepository.findByEmail(request.getAssigneeEmail())
            .orElseThrow(() -> new ResourceNotFoundException(
                "Assignee not found: " + request.getAssigneeEmail()));

    task.setAssignee(assignee);
    return TaskResponse.from(taskRepository.save(task));
}

@Override
@Transactional(readOnly = true)
public List<TaskResponse> getAssignedToMe(String email) {
    User user = getUser(email);
    return taskRepository.findByAssignee(user)
            .stream()
            .map(TaskResponse::from)
            .collect(Collectors.toList());
}

    @Override
    @Transactional(readOnly = true)
    public Page<TaskResponse> getAllTasks(String email, Pageable pageable) {
        return taskRepository.findByOwner(getUser(email), pageable)
                .map(TaskResponse::from);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id, String email) {
        return TaskResponse.from(
            taskRepository.findByIdAndOwner(id, getUser(email))
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id))
        );
    }

    @Override
    @Transactional
    public TaskResponse updateTask(Long id, TaskRequest request, String email) {
        Task task = taskRepository.findByIdAndOwner(id, getUser(email))
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus() != null ? request.getStatus() : task.getStatus());
        task.setDeadline(request.getDeadline());

        return TaskResponse.from(taskRepository.save(task));
    }

    @Override
    @Transactional
    public void deleteTask(Long id, String email) {
        Task task = taskRepository.findByIdAndOwner(id, getUser(email))
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        taskRepository.delete(task);
    }
}