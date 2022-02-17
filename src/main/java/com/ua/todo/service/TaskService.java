package com.ua.todo.service;

import com.ua.todo.dto.TaskDto;
import com.ua.todo.model.Task;

import java.util.List;

public interface TaskService {

    Task getById(Long id);

    Task save(TaskDto taskDto);

    Task update(Long id, TaskDto taskDto);

    void delete(Long id);

    List<Task> getAll();

}
