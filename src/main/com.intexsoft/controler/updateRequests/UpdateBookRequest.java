package controler.updateRequests;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateBookRequest {
    private UUID bookIds;
    private String bookBooknames;
    private String bookAuthors;
    private Integer bookCostInByns;
    private Integer bookCountInStocks;

    public UpdateBookRequest setBookId(UUID book_id) {
        setBookIds(book_id);
        return this;
    }

    public UpdateBookRequest setBookBookname(String bookName) {
        setBookBooknames(bookName);
        return this;
    }

    public UpdateBookRequest setBookAuthor(String author) {
        setBookAuthors(author);
        return this;
    }

    public UpdateBookRequest setBookCostInByn(int costInByn) {
        setBookCostInByns(costInByn);
        return this;
    }


    public UpdateBookRequest setBookCountInStock(int countInStock) {
        setBookCountInStocks(countInStock);
        return this;
    }

}
