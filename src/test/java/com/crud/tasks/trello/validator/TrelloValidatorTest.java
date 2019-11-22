package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloValidatorTest {

    @Autowired
    TrelloValidator trelloValidator;

    @Test
    public void testValidateTrelloBoards() {
        //Given
        List<TrelloBoard> boardsList = new ArrayList<>();
        TrelloBoard trelloTestBoard = new TrelloBoard("Id", "test", new ArrayList<>());
        TrelloBoard trelloBoard = new TrelloBoard("Id2", "name2", new ArrayList<>());
        boardsList.add(trelloTestBoard);
        boardsList.add(trelloBoard);

        //When
        List<TrelloBoard> filteredList = trelloValidator.validateTrelloBoards(boardsList);

        //Then
        assertEquals(1, filteredList.size());
        assertEquals("Id2", filteredList.get(0).getId());
        assertEquals("name2", filteredList.get(0).getName());
    }

}