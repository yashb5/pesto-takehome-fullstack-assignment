package com.pesto.takehomefullstackassignment.controller;

import com.pesto.takehomefullstackassignment.entity.Task;
import com.pesto.takehomefullstackassignment.entity.TaskStatus;
import com.pesto.takehomefullstackassignment.entity.User;
import com.pesto.takehomefullstackassignment.model.TaskRequest;
import com.pesto.takehomefullstackassignment.model.TaskUpdateRequest;
import com.pesto.takehomefullstackassignment.repository.TaskRepository;
import com.pesto.takehomefullstackassignment.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class TodoRestController {

    private TaskRepository taskRepository;
    private UserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    @GetMapping("todo")
    public List<Task> getToDoListStatusFilter(@RequestParam(required = false) TaskStatus status) {
        User currentUser = getCurrentUser();
        return status == null ?
                taskRepository.findTasksByUserAndStatusIsNot(currentUser, TaskStatus.DELETED) :
                taskRepository.findTasksByUserAndStatus(currentUser, status);
    }

    @PostMapping("todo")
    public Task addTask(@Valid @RequestBody TaskRequest taskRequest) {
        User currentUser = getCurrentUser();
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        task.setUser(currentUser);
        return taskRepository.save(task);
    }

    @PutMapping("todo/{taskId}")
    public void updateTask(@PathVariable @NotNull Long taskId, @Valid @RequestBody TaskUpdateRequest taskUpdateRequest) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findByIdAndUser(taskId, currentUser).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with Id: " + taskId)
        );

        task.setStatus(taskUpdateRequest.getStatus());
        taskRepository.save(task);
    }
}
