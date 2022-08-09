package com.intexsoft.model.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdateOrderStatusDTO {
    private String orderId;
    private Integer statusId;
}
