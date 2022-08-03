package com.intexsoft.model.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDTO {
    private String cartId;
    private String personId;
    private String cartname;
}
