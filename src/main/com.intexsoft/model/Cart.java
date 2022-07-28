package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@NoArgsConstructor
@Data
public class Cart {
    private UUID cartId;
    private UUID personId;
    private String cartname;
    private List<CartHasBook> cartHasBooks=new ArrayList<>();

}
