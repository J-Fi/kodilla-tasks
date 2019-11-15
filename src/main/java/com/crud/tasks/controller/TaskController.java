package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/v1/")
public class TaskController {
    @Autowired
    private DbService dbService;

    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, value = "tasks")
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(dbService.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "tasks/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) throws TaskNotFoundException {
        //return new TaskDto(1L, "test_title", "test_content");
        return taskMapper.mapToTaskDto(dbService.getTask(taskId).orElseThrow(TaskNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "tasks/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        dbService.deleteTask(taskId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "tasks", consumes = "application/json")
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        //return new TaskDto(1L, "Edited test_title", "Edited test_content");
        return taskMapper.mapToTaskDto(dbService.saveTask(taskMapper.mapToTask(taskDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "tasks", consumes = "application/json")
    public void createTask(@RequestBody TaskDto taskDto) {
        dbService.saveTask(taskMapper.mapToTask(taskDto));
    }

    /*@RequestMapping(method = RequestMethod.GET, value = "getTaskById")
    public TaskDto getTaskById(@RequestParam Long taskId) {
        return taskMapper.mapToTaskDto(dbService.getTaskById(taskId));
    }*/
}
