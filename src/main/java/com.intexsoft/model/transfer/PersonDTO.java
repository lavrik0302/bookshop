package com.intexsoft.model.transfer;

import com.intexsoft.model.Cart;
import com.intexsoft.model.PersonOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
