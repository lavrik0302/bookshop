package model;

import lombok.Data;

import java.util.UUID;

@Data
public class Cart {
    private UUID cart_id;
    private UUID person_id;
    private String cartname;

    public Cart() {
    }
}
