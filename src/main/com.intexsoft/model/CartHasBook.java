package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CartHasBook {

    private UUID cartId;
    private UUID bookId;
    private Integer bookCount;
}
