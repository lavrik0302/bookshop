package com.intexsoft.model.transfer;

import com.intexsoft.model.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Data
@NoArgsConstructor
public class PersonOrderDTO {
    private String orderId;
    private String personId;
    private String adress;
    private Integer statusId;
    private BookDTO[] books;
}
