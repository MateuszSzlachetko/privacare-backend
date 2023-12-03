package com.privacare.controller;

import com.privacare.model.dto.request.UserRequestDTO;
import com.privacare.model.dto.response.UserResponseDTO;
import com.privacare.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @GetMapping(params = {"peselFragment"})
    public ResponseEntity<List<UserResponseDTO>> getUsersByPeselFragment(@RequestParam(required = false) String peselFragment) {
        List<UserResponseDTO> result = this.userService.getUsersByPeselFragment(peselFragment);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserBy(@PathVariable UUID id) {
        UserResponseDTO result = this.userService.getUserResponseDTOBy(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(params = {"authId"})
    public ResponseEntity<UserResponseDTO> getUsersByAuthId(@RequestParam(required = false) String authId) {
        UserResponseDTO result = this.userService.getUsersByAuthId(authId);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO result = this.userService.addUser(userRequestDTO);
        return ResponseEntity.ok().body(result);
    }
}