package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@NoArgsConstructor
@Data
public class Cart {
    private UUID cartId;
    private UUID personId;
    private String cartname;


}
