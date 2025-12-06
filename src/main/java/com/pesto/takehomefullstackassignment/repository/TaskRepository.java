package com.pesto.takehomefullstackassignment.repository;

import com.pesto.takehomefullstackassignment.entity.Task;
import com.pesto.takehomefullstackassignment.entity.TaskStatus;
import com.pesto.takehomefullstackassignment.entity.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends ListCrudRepository<Task, Long> {
    List<Task> findTasksByUserAndStatusIsNot(User user, TaskStatus status);
    List<Task> findTasksByUserAndStatus(User user, TaskStatus status);
    Optional<Task> findByIdAndUser(Long id, User user);
}
