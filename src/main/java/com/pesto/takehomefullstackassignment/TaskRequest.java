package com.pesto.takehomefullstackassignment;

import com.pesto.takehomefullstackassignment.entity.TaskStatus;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class TaskRequest implements Serializable {
    @NotBlank
    private String title;
    private String description;
    @NotNull
    private TaskStatus status;
}
