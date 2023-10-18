package com.pesto.takehomefullstackassignment.repository;

import com.pesto.takehomefullstackassignment.entity.Task;
import com.pesto.takehomefullstackassignment.entity.TaskStatus;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends ListCrudRepository<Task, Long> {
    List<Task> findTasksByStatus (TaskStatus status);
}
