package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbServiceTest {

    @Autowired
    DbService dbService;

    @MockBean
    TaskRepository taskRepository;

    @Test
    public void shouldGetAllTasks() {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "title1", "contenet1"));
        taskList.add(new Task(2L, "title2", "contenet2"));
        when(taskRepository.findAll()).thenReturn(taskList);

        //When
        List<Task> list = dbService.getAllTasks();

        //Then
        assertEquals(2, list.size());
    }

    @Test
    public void shouldSaveTask() {
        //Given
        Task task = new Task(1L, "title1", "content1");
        when(taskRepository.save(task)).thenReturn(task);

        //When
        Task returnedTask = dbService.saveTask(task);

        //Then
        assertEquals(Optional.of(1L).get(), returnedTask.getId());
        assertEquals("title1", returnedTask.getTitle());
        assertEquals("content1", returnedTask.getContent());
    }

    @Test
    public void shouldGetNoTask() {
        //Given
        when(taskRepository.findById(2L)).thenReturn(null);

        //When
        Optional<Task> noSuchTask = dbService.getTask(2L);

        //Then
        assertNull(noSuchTask);
    }

    @Test
    public void shouldGetTask() {
        //Given
        Task task = new Task(1L, "title1", "content1");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        //When
        Optional<Task> returnedTask = dbService.getTask(1L);

        //Then
        assertEquals(returnedTask, Optional.of(task));
    }

    @Test
    public void shouldDeleteTask() {
        //Given

        //When
        dbService.deleteTask(anyLong());

        //Then
        verify(taskRepository,  times(1)).deleteById(anyLong());

    }
}