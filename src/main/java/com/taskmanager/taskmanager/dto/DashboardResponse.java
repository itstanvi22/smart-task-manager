package com.taskmanager.taskmanager.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class DashboardResponse {
    private long totalTasks;
    private long completedTasks;
    private long inProgressTasks;
    private long todoTasks;
    private double completionRate;        // percentage
    private long overdueTask;
    private Map<String, Long> tasksByPriority;
    private long assignedToMe;
    private long assignedByMe;
}