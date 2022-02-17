package com.ua.todo.controller;

import com.ua.todo.dto.TaskDto;
import com.ua.todo.model.Task;
import com.ua.todo.service.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskServiceImpl toDoService;

    @Autowired
    public TaskController(TaskServiceImpl toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping
    public Task create(@Valid @RequestBody TaskDto taskDto) {
        return toDoService.save(taskDto);
    }

    @GetMapping
    public List<Task> findAll() {
        return toDoService.getAll();
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        return toDoService.update(id, taskDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable Long id) {
        toDoService.delete(id);
    }

    @GetMapping("/{id}")
    public List<Task> findById(@PathVariable Long id) {
        return Arrays.asList(toDoService.getById(id));
    }
}
