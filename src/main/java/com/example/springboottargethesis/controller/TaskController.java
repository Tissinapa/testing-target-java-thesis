package com.example.springboottargethesis.controller;


import com.example.springboottargethesis.dto.TaskCreateRequest;
import com.example.springboottargethesis.dto.TaskResponse;
import com.example.springboottargethesis.dto.TaskUpdateRequest;
import com.example.springboottargethesis.model.Task;
import com.example.springboottargethesis.repository.TaskRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@SecurityRequirement(name="bearerAuth")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<TaskResponse> getTasks(){
        return taskRepository.findAll().stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskCreateRequest request) {

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        Task saved = taskRepository.save(task);
        return ResponseEntity.ok(toResponse(saved));
    }
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(task -> ResponseEntity.ok(toResponse(task)))
                .orElse(ResponseEntity.ok(new TaskResponse()));
    }
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @RequestBody TaskUpdateRequest request) {
        return taskRepository.findById(id)
                .map(task -> {
                    if (request.getTitle() != null) task.setTitle(request.getTitle());
                    if (request.getDescription() != null) task.setDescription(request.getDescription());
                    if (request.getPriority() != null) task.setPriority(request.getPriority());
                    if (request.getCompleted() != null) task.setCompleted(request.getCompleted());
                    return ResponseEntity.ok(toResponse(taskRepository.save(task)));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);
                    return ResponseEntity.ok(toResponse(task));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    private TaskResponse toResponse(Task task){
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setPriority(task.getPriority());
        response.setCompleted(task.getCompleted());
        response.setCreatedAt(task.getCreatedAt());
        return response;
    }

}
