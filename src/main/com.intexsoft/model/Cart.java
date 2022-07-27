package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@NoArgsConstructor
@Data
public class Cart {
    private UUID cart_id;
    private UUID person_id;
    private String cartname;


}
