package com.intexsoft.model.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CartHasBookDTO {
    private String cartId;
    private String bookId;
    private Integer bookCount;
}
