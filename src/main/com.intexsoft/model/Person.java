package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Person {

    private UUID personId;
    private String name;
    private String surname;
    private String mobilenumber;
    private Cart personCart;

}
