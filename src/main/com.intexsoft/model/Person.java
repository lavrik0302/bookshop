package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Person {

    private UUID personId;
    private String name;
    private String surname;
    private String mobilenumber;
    private Cart personCart;
    private List<PersonOrder> personOrders = new ArrayList<>();

}
