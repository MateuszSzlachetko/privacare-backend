package com.privacare.controller;

import com.privacare.model.dto.request.TaskEditRequestDTO;
import com.privacare.model.dto.request.TaskRequestDTO;
import com.privacare.model.dto.response.TaskResponseDTO;
import com.privacare.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/tasks")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getTasksByCategory(@RequestParam Integer categoryId) {
        List<TaskResponseDTO> result = this.taskService.getTasksByCategory(categoryId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable UUID taskId) {
        TaskResponseDTO result = this.taskService.getTaskBy(taskId);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> addTask(@RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO result = this.taskService.addTask(taskRequestDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping
    public ResponseEntity<Integer> editTask(@RequestBody @Valid TaskEditRequestDTO taskEditRequestDTO) {
        Integer result = this.taskService.editTask(taskEditRequestDTO);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Object> deleteTask(@PathVariable UUID taskId) {
        this.taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}