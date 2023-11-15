package com.privacare.controller;

import com.privacare.model.dto.response.UserResponseDTO;
import com.privacare.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsersByPeselFragment(@RequestParam String peselFragment) {
        List<UserResponseDTO> result = this.userService.getUsersByPeselFragment(peselFragment);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserBy(@PathVariable UUID id) {
        UserResponseDTO result = this.userService.getUserResponseDTOBy(id);
        return ResponseEntity.ok().body(result);
    }
}