package com.privacare.service;

import com.privacare.model.dto.request.TaskEditRequestDTO;
import com.privacare.model.dto.request.TaskRequestDTO;
import com.privacare.model.dto.response.TaskResponseDTO;
import com.privacare.model.entity.Category;
import com.privacare.model.entity.Task;
import com.privacare.model.entity.User;
import com.privacare.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final UserService userService;

    private final TaskRepository taskRepository;

    private final CategoryService categoryService;

    public List<TaskResponseDTO> getTasksByCategory(Integer id) {
        Category category = this.categoryService.getCategoryBy(id);

        return this.taskRepository.findByCategoryIdOrderByCreatedAtDesc(category.getId()).stream()
                        .map(TaskService::mapTaskToTaskResponse).collect(Collectors.toList());
    }

    public TaskResponseDTO addTask(TaskRequestDTO taskRequestDTO) {
        User creator = this.userService.getUserBy(taskRequestDTO.getCreatorId());
        Category category = this.categoryService.getCategoryBy(taskRequestDTO.getCategoryId());

        Task task = Task.builder()
                .creator(creator)
                .createdAt(LocalDateTime.now())
                .content(taskRequestDTO.getContent())
                .category(category)
                .state(taskRequestDTO.getState())
                .build();

        this.taskRepository.save(task);

        return mapTaskToTaskResponse(task);
    }

    public Integer editTask(TaskEditRequestDTO taskEditRequestDTO) {
        this.taskRepository.findById(taskEditRequestDTO.getId()).orElseThrow(
                () -> new NoSuchElementException("News with id: " + taskEditRequestDTO.getId() + " not found"));

        return this.taskRepository.updateTaskBy(
                taskEditRequestDTO.getId(),
                taskEditRequestDTO.getContent(),
                taskEditRequestDTO.getState().toUpperCase());
    }

    public void deleteTask(UUID taskId) throws EmptyResultDataAccessException {
        this.taskRepository.deleteById(taskId);
    }

    public TaskResponseDTO getTaskBy(UUID taskId) {
        Task task = this.taskRepository.findById(taskId).orElseThrow(
                () -> new NoSuchElementException("Task with id: " + taskId + " not found"));

        return mapTaskToTaskResponse(task);
    }

    private static TaskResponseDTO mapTaskToTaskResponse(Task task){
        return TaskResponseDTO.builder()
                .id(task.getId())
                .creatorId(task.getCreator().getId())
                .createdAt(task.getCreatedAt())
                .content(task.getContent())
                .categoryId(task.getCategory().getId())
                .state(task.getState())
                .build();
    }
}
