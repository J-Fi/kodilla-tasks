package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldFetchTasks() throws Exception {
        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(1L, "Task1", "TaskContent1"));

        when(taskMapper.mapToTaskDtoList(any())).thenReturn(taskDtoList);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Task1")))
                .andExpect(jsonPath("$[0].content", is("TaskContent1")));
    }

    @Test
    public void shouldFetchTask() throws Exception {
        //Given
        Task task = new Task(1L, "Task1", "TaskContent1");
        TaskDto taskDto = new TaskDto(1L, "TaskDto1", "TaskDtoContent1");

        when(dbService.getTask(task.getId())).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(dbService.getTask(taskDto.getId()).orElseThrow(TaskNotFoundException::new))).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("TaskDto1")))
                .andExpect(jsonPath("$.content", is("TaskDtoContent1")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given
        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "TestTitle1", "TestContent1");
        TaskDto taskDtoAfterUpdate = new TaskDto(1L, "TestTitle2", "TestContent2");

        when(taskMapper.mapToTaskDto(dbService.saveTask(taskMapper.mapToTask(any(TaskDto.class))))).thenReturn(taskDtoAfterUpdate);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("TestTitle2")))
                .andExpect(jsonPath("$.content", is("TestContent2")));
    }

    @Test
    public void createTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "TaskDto1", "TaskDtoContent1");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFindTaskById() throws Exception {
        //Given
        Task task = new Task(1L, "Task1", "TaskContent1");
        TaskDto taskDto = new TaskDto(1L, "TaskDto1", "TaskDtoContent1");

        when(taskMapper.mapToTaskDto(any())).thenReturn(taskDto);

        //When & Then
            mockMvc.perform(get("/v1/task/getTaskById?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("TaskDto1")))
                .andExpect(jsonPath("$.content", is("TaskDtoContent1")));
    }

}