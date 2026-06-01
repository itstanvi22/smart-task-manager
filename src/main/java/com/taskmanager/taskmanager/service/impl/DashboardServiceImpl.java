package com.taskmanager.taskmanager.service.impl;

import com.taskmanager.taskmanager.dto.DashboardResponse;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.repository.UserRepository;
import com.taskmanager.taskmanager.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public DashboardResponse getDashboard(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        long total       = taskRepository.countByOwner(user);
        long completed   = taskRepository.countByOwnerAndStatus(user, Task.Status.DONE);
        long inProgress  = taskRepository.countByOwnerAndStatus(user, Task.Status.IN_PROGRESS);
        long todo        = taskRepository.countByOwnerAndStatus(user, Task.Status.TODO);
        long overdue     = taskRepository.countOverdue(user, LocalDateTime.now());
        long assignedToMe   = taskRepository.countByAssignee(user);
        long assignedByMe   = taskRepository.countAssignedByMe(user);

        double completionRate = total == 0 ? 0 :
                Math.round((completed * 100.0 / total) * 10.0) / 10.0;

        Map<String, Long> byPriority = Map.of(
            "HIGH",   taskRepository.countByOwnerAndPriority(user, Task.Priority.HIGH),
            "MEDIUM", taskRepository.countByOwnerAndPriority(user, Task.Priority.MEDIUM),
            "LOW",    taskRepository.countByOwnerAndPriority(user, Task.Priority.LOW)
        );

        return DashboardResponse.builder()
                .totalTasks(total)
                .completedTasks(completed)
                .inProgressTasks(inProgress)
                .todoTasks(todo)
                .completionRate(completionRate)
                .overdueTask(overdue)
                .tasksByPriority(byPriority)
                .assignedToMe(assignedToMe)
                .assignedByMe(assignedByMe)
                .build();
    }
}