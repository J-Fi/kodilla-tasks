package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {
    @Autowired
    TrelloMapper trelloMapper;

    @Test
    public void testMapToList() {
        //Given
        List<TrelloListDto> listDto = new ArrayList<>();
        TrelloListDto trelloListDto1 = new TrelloListDto("TrelloListName1", "TrelloList1", true);
        listDto.add(trelloListDto1);

        //When
        List<TrelloList> list = trelloMapper.mapToList(listDto);

        //Then
        assertEquals(1, list.size());
        assertEquals("TrelloListName1", list.get(0).getId());
        assertEquals("TrelloList1", list.get(0).getName());
        assertTrue(list.get(0).isClosed());

    }

    @Test
    public void testMapToBoards() {
        //Given
        List<TrelloListDto> listDto = new ArrayList<>();
        TrelloListDto trelloListDto1 = new TrelloListDto("TrelloListName1", "TrelloList1", true);
        TrelloListDto trelloListDto2 = new TrelloListDto("TrelloListName2", "TrelloList2", true);
        listDto.add(trelloListDto1);
        listDto.add(trelloListDto2);

        List<TrelloBoardDto> boardDto = new ArrayList<>();
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("TrelloBoardName1", "TrelloBoard1",listDto);
        boardDto.add(trelloBoardDto1);

        //When
        List<TrelloBoard> list = trelloMapper.mapToBoards(boardDto);

        //Then
        assertEquals(1, list.size());
        assertEquals(2, list.get(0).getLists().size());
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("CardName1", "CardDescription", "CardPos", "CardListId");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals("CardName1", trelloCard.getName());
        assertEquals("CardDescription", trelloCard.getDescription());
        assertEquals("CardPos", trelloCard.getPos());
        assertEquals("CardListId", trelloCard.getListId());

    }

    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloList> list = new ArrayList<>();
        TrelloList trelloList1 = new TrelloList("TrelloListName1", "TrelloList1", true);
        list.add(trelloList1);

        //When
        List<TrelloListDto> listDto = trelloMapper.mapToListDto(list);

        //Then
        assertEquals(1, list.size());
        assertEquals("TrelloListName1", listDto.get(0).getId());
        assertEquals("TrelloList1", listDto.get(0).getName());
        assertTrue(listDto.get(0).isClosed());

    }

    @Test
    public void testMapToBoardDto() {
        //Given
        List<TrelloList> list = new ArrayList<>();
        TrelloList trelloList1 = new TrelloList("TrelloListName1", "TrelloList1", true);
        TrelloList trelloList2 = new TrelloList("TrelloListName2", "TrelloList2", true);
        list.add(trelloList1);
        list.add(trelloList2);

        List<TrelloBoard> board = new ArrayList<>();
        TrelloBoard trelloBoard1 = new TrelloBoard("TrelloBoardName1", "TrelloBoard1",list);
        board.add(trelloBoard1);

        //When
        List<TrelloBoardDto> listDto = trelloMapper.mapToBoardDto(board);

        //Then
        assertEquals(1, listDto.size());
        assertEquals(2, listDto.get(0).getLists().size());
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("CardName1", "CardDescription", "CardPos", "CardListId");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals("CardName1", trelloCardDto.getName());
        assertEquals("CardDescription", trelloCardDto.getDescription());
        assertEquals("CardPos", trelloCardDto.getPos());
        assertEquals("CardListId", trelloCardDto.getListId());

    }
}
