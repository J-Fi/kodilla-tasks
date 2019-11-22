package com.crud.tasks.service;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloServiceTest {

    @Autowired
    TrelloService trelloService;

    @MockBean
    TrelloClient trelloClient;

    @MockBean
    SimpleEmailService simpleEmailService;

    @Test
    public void testCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Name1", "Desc1", "Pos1", "listId1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("Id2", "Name2", "test@test.ts");

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto createdTrelloCardDtoReturned = trelloService.createTrelloCard(trelloCardDto);

        //Then
        assertEquals("Id2", createdTrelloCardDtoReturned.getId());
        assertEquals("Name2", createdTrelloCardDtoReturned.getName());
        assertEquals("test@test.ts", createdTrelloCardDtoReturned.getShortUrl());
    }
    @Test
    public void testFetchTrelloBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("Id1", "Name1", new ArrayList<>());
        trelloBoardDtoList.add(trelloBoardDto);

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtoList);

        //When
        List<TrelloBoardDto> trelloBoardDtoListReturned = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(1, trelloBoardDtoListReturned.size());
    }
}