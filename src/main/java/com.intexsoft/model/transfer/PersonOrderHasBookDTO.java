package com.intexsoft.model.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
public class PersonOrderHasBookDTO {
    private String orderId;
    private String bookId;
    private Integer bookCount;
}
