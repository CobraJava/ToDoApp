package com.ua.todo.controller;

import com.ua.todo.dto.LoginDto;
import com.ua.todo.dto.UserDataDto;
import com.ua.todo.model.User;
import com.ua.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public String login(@RequestBody LoginDto loginDto) {
        return userService.signin(loginDto);
    }

    @PostMapping("/signup")
    public String signup(@Valid @RequestBody UserDataDto user) {
        return userService.signup(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable Long id){ userService.delete(id);}

    @GetMapping(value = "/{username}")
    public User search(@PathVariable String username) {
        return userService.search(username);
    }

    @GetMapping(value = "/me")

    public User whoami(HttpServletRequest req) {
        return userService.whoami(req);
    }

    @GetMapping("/refresh")
    public String refresh(HttpServletRequest req) {
        return userService.refresh(req.getRemoteUser());
    }

}

