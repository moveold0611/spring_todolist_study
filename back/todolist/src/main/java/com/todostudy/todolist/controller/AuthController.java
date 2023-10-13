package com.todostudy.todolist.controller;

import com.todostudy.todolist.dto.SigninReqDto;
import com.todostudy.todolist.dto.SignupReqDto;
import com.todostudy.todolist.exception.SignupException;
import com.todostudy.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupReqDto signupReqDto, BindingResult bindingResult) {

        // AOP
        if(bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errorMap.put(error.getField(), error.getDefaultMessage());
            });
            throw new SignupException(errorMap);
        }

        if(userService.checkDuplicated(signupReqDto.getEmail())) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("message", "중복된 이메일입니다.");
            throw new SignupException(errorMap);
        }
        userService.signupUser(signupReqDto);
        return ResponseEntity.ok().body(200);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> test(@RequestBody SigninReqDto signinReqDto) {
        String accessToken = userService.signinUser(signinReqDto);
        System.out.println(accessToken);
        return ResponseEntity.ok().body(accessToken);
    }



}
