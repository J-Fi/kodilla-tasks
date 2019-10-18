package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.service.TrelloService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TrelloFacade {

    @Autowired
    TrelloService trelloService;

    public List<TrelloBoard> fetchTrelloBoards() {
        List<TrelloBoardDto> trelloBoards = trelloService.fetchTrelloBoards();
    }
}
