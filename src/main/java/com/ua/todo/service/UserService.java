package com.ua.todo.service;

import com.ua.todo.dto.LoginDto;
import com.ua.todo.dto.UserDataDto;
import com.ua.todo.exception.CustomException;
import com.ua.todo.model.Role;
import com.ua.todo.model.User;
import com.ua.todo.repository.UserRepository;
import com.ua.todo.security.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }

    public String signin(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            return jwtTokenProvider.createToken(
                    loginDto.getUsername(),
                    Collections.singletonList(userRepository.findByUsername(loginDto.getUsername()).getRole())
            );
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new CustomException("authentication error", HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    public String signup(UserDataDto user) {
        if (!userRepository.existsByUsername(user.getUsername())) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User userEntity = userRepository.save(modelMapper.map(user, User.class));
            userEntity.setRole(Role.USER);
            userRepository.save(userEntity);

            return jwtTokenProvider.createToken(user.getUsername(), Collections.singletonList(Role.USER));
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User search(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public User whoami(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, Collections.singletonList(userRepository.findByUsername(username).getRole()));
    }

}
