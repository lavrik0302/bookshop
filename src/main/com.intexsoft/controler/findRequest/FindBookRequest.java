package controler.findRequest;

import controler.findRequest.fromTo.BookFromToCostInByns;

import controler.findRequest.fromTo.BookFromToCountInStocks;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class FindBookRequest {

    private List<UUID> bookIds = new ArrayList<>();
    private List<String> bookBooknames = new ArrayList<>();
    private List<String> bookAuthors = new ArrayList<>();
    private BookFromToCostInByns bookFromToCostInByns=new BookFromToCostInByns();
    private Integer bookCostInByns;
    private Integer bookCountInStocks;
    private BookFromToCountInStocks bookFromToCountInStocks=new BookFromToCountInStocks();

    public FindBookRequest setBookId(UUID book_id) {
        getBookIds().add(book_id);
        return this;
    }

    public FindBookRequest setBookId(List<UUID> book_ids) {
        getBookIds().addAll(book_ids);
        return this;
    }

    public FindBookRequest setBookBookname(String bookName) {
        getBookBooknames().add(bookName);
        return this;
    }

    public FindBookRequest setBookBookname(List<String> booknames) {
        getBookBooknames().addAll(booknames);
        return this;
    }

    public FindBookRequest setBookAuthor(String author) {
        getBookAuthors().add(author);
        return this;
    }

    public FindBookRequest setBookAuthor(List<String> authors) {
        getBookAuthors().addAll(authors);
        return this;
    }

    public FindBookRequest setBookCostInByn(int costInByn) {
        setBookCostInByns(costInByn);
        return this;
    }

    public FindBookRequest setFromToCostInByns(BookFromToCostInByns bookFromToCostInByns) {
        setBookFromToCostInByns(bookFromToCostInByns);
        return this;
    }


    public FindBookRequest setBookCountInStock(int countInStock) {
        setBookCountInStocks(countInStock);
        return this;
    }

    public FindBookRequest setFromToCountInStocks(BookFromToCountInStocks bookFromToCountInStocks) {
        setBookFromToCountInStocks(bookFromToCountInStocks);
        return this;
    }


}
