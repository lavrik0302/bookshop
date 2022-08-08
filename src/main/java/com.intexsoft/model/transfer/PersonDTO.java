package com.intexsoft.model.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonDTO {
    private String personId;
    private String name;
    private String surname;
    private String mobilenumber;
    private CartDTO personCart;
    private PersonOrderDTO[] personOrders;
}
