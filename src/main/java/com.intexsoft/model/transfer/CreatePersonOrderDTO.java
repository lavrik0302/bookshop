package com.intexsoft.model.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreatePersonOrderDTO {
     private String personId;
     private String adress;
     private Integer statusId;

}
