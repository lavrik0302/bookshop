package com.intexsoft.model.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDTO {
        private String bookId;
        private String bookname;
        private String author;
        private Integer costInByn;
        private Integer countInStock;

}