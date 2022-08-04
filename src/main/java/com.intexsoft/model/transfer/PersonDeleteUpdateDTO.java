package com.intexsoft.model.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PersonDeleteUpdateDTO {
private String personId;
private String name;
private String surname;
private String mobilenumber;
}
