package com.pesto.takehomefullstackassignment.controller;

import com.pesto.takehomefullstackassignment.entity.Task;
import com.pesto.takehomefullstackassignment.entity.TaskStatus;
import com.pesto.takehomefullstackassignment.model.TaskRequest;
import com.pesto.takehomefullstackassignment.model.TaskUpdateRequest;
import com.pesto.takehomefullstackassignment.repository.TaskRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class TodoRestController {


    private TaskRepository taskRepository;

    @GetMapping("todo")
    public List<Task> getToDoListStatusFilter(@RequestParam(required = false) TaskStatus status) {
        return status == null?
                taskRepository.findTasksByStatusIsNot(TaskStatus.DELETED) :
                taskRepository.findTasksByStatus(status);
    }

    @PostMapping("todo")
    public Task addTask(@Valid @RequestBody TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        return taskRepository.save(task);
    }

    @PutMapping("todo")
    public void updateTask(@Valid @RequestBody TaskUpdateRequest taskUpdateRequest) {
        Task task = taskRepository.findById(taskUpdateRequest.getTaskId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with Id: " + taskUpdateRequest.getTaskId())
        );

        task.setStatus(TaskStatus.DELETED);
        taskRepository.save(task);
    }
}
