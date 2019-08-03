package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {
    List<TaskDto> getTasks() {
        return new ArrayList<>();
    }

    public TaskDto getTask(Long taskId) {
        return new TaskDto(1L, "test_title", "test_content");
    }

    public void deleteTask(Long taskId) {

    }

    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "Edited test_title", "Edited test_content");
    }

    public void createTask(TaskDto taskDto) {

    }
}
