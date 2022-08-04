package com.intexsoft.model.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteBookFromCartDTO {
private String cartId;
private String bookId;
}
