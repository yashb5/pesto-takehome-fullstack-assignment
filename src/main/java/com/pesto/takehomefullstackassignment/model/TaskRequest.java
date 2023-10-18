package com.pesto.takehomefullstackassignment.model;

import com.pesto.takehomefullstackassignment.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class TaskRequest implements Serializable {
    @NotBlank
    private String title;
    private String description;
    @NotNull
    private TaskStatus status;
}
