package com.intexsoft.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class PersonOrderHasBook {
    private UUID orderId;
    private UUID bookId;
    private Integer bookCount;
}
