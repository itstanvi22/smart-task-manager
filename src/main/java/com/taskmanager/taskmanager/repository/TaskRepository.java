package com.taskmanager.taskmanager.repository;

import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByOwner(User owner);
    Optional<Task> findByIdAndOwner(Long id, User owner);
    List<Task> findByAssignee(User assignee);

    // Count by owner and status
    long countByOwnerAndStatus(User owner, Task.Status status);

    // Count by owner
    long countByOwner(User owner);

    // Count overdue tasks (deadline passed and not done)
    @Query("SELECT COUNT(t) FROM Task t WHERE t.owner = :owner " +
           "AND t.deadline < :now AND t.status != 'DONE'")
    long countOverdue(@Param("owner") User owner, @Param("now") LocalDateTime now);

    // Count by owner and priority
    long countByOwnerAndPriority(User owner, Task.Priority priority);

    // Count tasks assigned to user by others
    long countByAssignee(User assignee);

    // Count tasks owner has assigned to others
    @Query("SELECT COUNT(t) FROM Task t WHERE t.owner = :owner " +
           "AND t.assignee IS NOT NULL AND t.assignee != :owner")
    long countAssignedByMe(@Param("owner") User owner);

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.owner LEFT JOIN FETCH t.assignee WHERE t.owner = :owner")
    List<Task> findByOwnerWithUsers(@Param("owner") User owner);

    Page<Task> findByOwner(User owner, Pageable pageable);
}