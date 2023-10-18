package com.pesto.takehomefullstackassignment.controller;

import com.pesto.takehomefullstackassignment.TaskRequest;
import com.pesto.takehomefullstackassignment.entity.Task;
import com.pesto.takehomefullstackassignment.entity.TaskStatus;
import com.pesto.takehomefullstackassignment.repository.TaskRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class TodoController {

    private TaskRepository taskRepository;

    @GetMapping("todo")
    public List<Task> getToDoListStatusFilter(@RequestParam TaskStatus status) {
        return status == null?
                taskRepository.findAll() :
                taskRepository.findTasksByStatus(status);
    }

    @PostMapping("todo")
    public void addTask(@Valid @RequestBody TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        taskRepository.save(task);
    }

}
