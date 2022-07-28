package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PersonOrder {
    private UUID orderId;
    private UUID personId;
    private String adress;
    private Integer statusId;
    private List<PersonOrderHasBook> orderHasBooks=new ArrayList<>();
}
