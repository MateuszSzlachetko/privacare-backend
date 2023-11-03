package com.privacare.controller;

import com.privacare.model.dto.response.UserResponseDTO;
import com.privacare.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsersByPeselFragment(
            @RequestParam String peselFragment) {
        List<UserResponseDTO> result = this.userService.getUsersByPeselFragment(peselFragment);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}