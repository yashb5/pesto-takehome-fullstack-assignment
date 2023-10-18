package com.pesto.takehomefullstackassignment.controller;

import com.pesto.takehomefullstackassignment.entity.Task;
import com.pesto.takehomefullstackassignment.entity.TaskStatus;
import com.pesto.takehomefullstackassignment.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class TodoController {

    private TaskRepository taskRepository;

    @GetMapping("todo")
    public List<Task> getToDoList() {
        return taskRepository.findAll();
    }

    @GetMapping("todo")
    public List<Task> getToDoListStatusFilter(@RequestParam TaskStatus status) {
        return taskRepository.findTasksByStatus(status);
    }


}
