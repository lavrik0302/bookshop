package com.intexsoft.model.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PesronInfoDTO {
    private String personId;
    private String name;
    private String surname;
    private String mobilenumber;
}
