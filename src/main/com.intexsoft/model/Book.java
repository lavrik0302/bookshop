package model;

import lombok.Data;

import java.util.UUID;

@Data
public class Book {
    private UUID book_id;
    private String bookname;
    private String author;
    private int costInByn;
    private int countInStock;

    public Book() {
    }
}
