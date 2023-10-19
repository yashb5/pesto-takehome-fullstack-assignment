package com.pesto.takehomefullstackassignment.model;

import com.pesto.takehomefullstackassignment.entity.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;


@Data
public class TaskUpdateRequest implements Serializable {
    @NotNull
    TaskStatus status;
}
