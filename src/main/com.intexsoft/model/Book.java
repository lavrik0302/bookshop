package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@NoArgsConstructor
@Data
public class Book {
    private UUID bookId;
    private String bookname;
    private String author;
    private int costInByn;
    private int countInStock;

}
