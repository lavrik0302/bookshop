package com.intexsoft.model.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonOrderDTO {
    private String orderId;
    private String personId;
    private String adress;
    private Integer statusId;
    private PersonOrderHasBookDTO[] books;
}
