package controler;

import lombok.Data;
import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class FindBookRequest {

    private List<UUID> bookIds = new ArrayList<>();
    private List<String> bookBooknames = new ArrayList<>();
    private List<String> bookAuthors = new ArrayList<>();
    private List<Integer> bookCostInByns = new ArrayList<>();
    private List<Integer> bookCountInStocks = new ArrayList<>();

    public FindBookRequest setBookId(UUID book_id) {
        getBookIds().add(book_id);
        return this;
    }
    public FindBookRequest setBookBookname(String bookName) {
        getBookBooknames().add(bookName);
        return this;
    }
    public FindBookRequest setBookAuthor(String author) {
        getBookAuthors().add(author);
        return this;
    }
    public FindBookRequest setBookCostInByn(int costInByn) {
        getBookCostInByns().add(costInByn);
        return this;
    }
    public FindBookRequest setBookCountInStock(int countInStock) {
        getBookCountInStocks().add(countInStock);
        return this;
    }
    public FindBookRequest setBookId(List<UUID> book_ids) {
        getBookIds().addAll(book_ids);
        return this;
    }
    public FindBookRequest setBookBookname(List<String> booknames) {
        getBookBooknames().addAll(booknames);
        return this;
    }
    public FindBookRequest setBookAuthor(List<String> authors) {
        getBookAuthors().addAll(authors);
        return this;
    }
    public FindBookRequest setBookCostInByn(List<Integer> costInByns) {
        getBookCostInByns().addAll(costInByns);
        return this;
    }
    public FindBookRequest setBookCountInStock(List<Integer> countInStock) {
        getBookCountInStocks().addAll(countInStock);
        return this;
    }

}
