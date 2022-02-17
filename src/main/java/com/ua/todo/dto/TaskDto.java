package com.ua.todo.dto;

import com.ua.todo.model.Status;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class TaskDto {

    @NotNull(message = "should not be null")
    @Size(min = 10, max = 1500, message = "name should be between 10 and 1500 symbols")
    private String name;

    @NotNull(message = "should not be null")
    private Status status;

}
