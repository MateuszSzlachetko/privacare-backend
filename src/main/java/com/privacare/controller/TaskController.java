package com.privacare.controller;

import com.privacare.model.dto.request.TaskEditRequestDTO;
import com.privacare.model.dto.request.TaskRequestDTO;
import com.privacare.model.dto.response.TaskResponseDTO;
import com.privacare.service.TaskService;
import com.privacare.utilities.exception.ErrorDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getTasksByCategory(
            @RequestParam Integer categoryId) {
        List<TaskResponseDTO> result = this.taskService.getTasksByCategory(categoryId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable UUID taskId) {
        TaskResponseDTO result = this.taskService.getTaskBy(taskId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> addTask(@RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO result = this.taskService.addTask(taskRequestDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Integer> editTask(@RequestBody @Valid TaskEditRequestDTO taskEditRequestDTO) {
        Integer result = this.taskService.editTask(taskEditRequestDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Object> deleteTask(@PathVariable UUID taskId) {
        try {
            this.taskService.deleteTask(taskId);
        } catch (EmptyResultDataAccessException e) {
            ErrorDetails errorDetails = ErrorDetails.builder()
                    .messages(List.of("Task with id: " + taskId + " not found"))
                    .timestamp(LocalDateTime.now())
                    .build();
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}