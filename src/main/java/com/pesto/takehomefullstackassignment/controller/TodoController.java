package com.pesto.takehomefullstackassignment.controller;

import com.pesto.takehomefullstackassignment.model.TaskRequest;
import com.pesto.takehomefullstackassignment.entity.Task;
import com.pesto.takehomefullstackassignment.entity.TaskStatus;
import com.pesto.takehomefullstackassignment.model.TaskUpdateRequest;
import com.pesto.takehomefullstackassignment.repository.TaskRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@AllArgsConstructor
public class TodoController {

    @GetMapping("/")
    public String index () {
        return "index";
    }

}
