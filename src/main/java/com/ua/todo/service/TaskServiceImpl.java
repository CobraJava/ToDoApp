package com.ua.todo.service;

import com.ua.todo.dto.TaskDto;
import com.ua.todo.exception.NotFoundException;
import com.ua.todo.model.Task;
import com.ua.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserService userService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public Task save(TaskDto toDoDto) {


        Task task = new Task();
        task.setName(toDoDto.getName());
        task.setStatus(toDoDto.getStatus());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        task.setUser(userService.search(user.getUsername()));
        return taskRepository.save(task);
    }

    @Override
    public Task update(Long id, TaskDto taskDto) {
        Task task = getById(id);
        task.setName(taskDto.getName());
        task.setStatus(taskDto.getStatus());

        return taskRepository.save(task);
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }
}
