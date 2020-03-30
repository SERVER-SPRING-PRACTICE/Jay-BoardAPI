package com.cadi.boardapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class Boards {

    private List<Item> BoardList;

    private class Item {
        private String title;
        private String content;
    }

}
