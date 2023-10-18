package com.pesto.takehomefullstackassignment.model;

import com.pesto.takehomefullstackassignment.entity.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class TaskUpdateRequest implements Serializable {

    Long taskId;
    @NotNull
    TaskStatus status;
}
