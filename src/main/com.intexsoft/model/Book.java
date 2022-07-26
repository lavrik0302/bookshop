package model;

import lombok.Data;

import java.util.UUID;

@Data
public class Book {
    private UUID book_id;
    private String bookname;
    private String author;
    private int cost_in_byn;
    private int count_in_stock;

    public Book() {
    }
}
