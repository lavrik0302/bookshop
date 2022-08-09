package com.intexsoft.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PersonOrder {
    private UUID orderId;
    private UUID personId;
    private String adress;
    private Integer statusId;
    private List<Book> books = new ArrayList<>();

    public String getStringStatusId() {
        switch (statusId) {
            case 1:
                return "availability check";
            case 2:
                return "waiting for payment";
            case 3:
                return "paid";
            case 4:
                return "at the post office";
            case 5:
                return "in way";
            case 6:
                return "delivered";
            default:
                return "incorrect statusId";
        }
    }
}
