package model;

import lombok.Data;

import java.util.UUID;

@Data
public class Person {

    private UUID person_id;
    private String name;
    private String surname;
    private String mobilenumber;

    public Person() {
    }

}
