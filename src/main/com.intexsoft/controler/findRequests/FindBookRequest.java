package controler.findRequests;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class FindBookRequest {

    private List<UUID> bookIds = new ArrayList<>();
    private List<String> bookBooknames = new ArrayList<>();
    private List<String> bookAuthors = new ArrayList<>();
    private Integer bookMoreThanCostInByns;
    private Integer bookLessThanCostInByns;
    private Integer bookCostInByns;
    private Integer bookCountInStocks;
    private Integer bookMoreThanCountInStocks;
    private Integer bookLessThanCountInStocks;

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

    public FindBookRequest setBookMoreThanCostInByn(int costInByn) {
        setBookMoreThanCostInByns(costInByn);
        return this;
    }

    public FindBookRequest setLessThanCostInByn(int costInByn) {
        setBookLessThanCostInByns(costInByn);
        return this;
    }

    public FindBookRequest setBookCountInStock(int countInStock) {
        setBookCountInStocks(countInStock);
        return this;
    }

    public FindBookRequest setBookMoreThanCountInStock(int countInStock) {
        setBookMoreThanCountInStocks(countInStock);
        return this;
    }

    public FindBookRequest setBookLessThanCountInStock(int countInStock) {
        setBookLessThanCountInStocks(countInStock);
        return this;
    }

    public String toSQLStringStatement() {
        StringBuilder sb = new StringBuilder();
        if (!getBookIds().isEmpty()) {
            sb.append("book_id ");
            if (getBookIds().size() > 1) {
                sb.append("in (");
                for (UUID book_id : getBookIds()) {
                    sb.append("'" + book_id + "', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='" + getBookIds().get(0) + "' AND ");
            }
        }
        if (!getBookBooknames().isEmpty()) {
            sb.append("bookname ");
            if (getBookBooknames().size() > 1) {
                sb.append("in (");
                for (String bookname : getBookBooknames()) {
                    sb.append("'" + bookname + "', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='" + getBookBooknames().get(0) + "' AND ");
            }
        }
        if (!getBookAuthors().isEmpty()) {
            sb.append("author ");
            if (getBookAuthors().size() > 1) {
                sb.append("in (");
                for (String author : getBookAuthors()) {
                    sb.append("'" + author + "', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='" + getBookAuthors().get(0) + "' AND ");
            }
        }
        if (getBookMoreThanCostInByns() != null) {
            sb.append("cost_in_byn ");
            sb.append(">='" + getBookMoreThanCostInByns() + "' AND ");
        }
        if (getBookLessThanCostInByns() != null) {
            sb.append("cost_in_byn ");
            sb.append("<='" + getBookLessThanCostInByns() + "' AND ");
        }
        if (getBookCostInByns() != null) {
            sb.append("cost_in_byn='" + getBookCostInByns() + "' AND ");
        }

        if (getBookMoreThanCountInStocks() != null) {
            sb.append("count_in_stock ");
            sb.append(">='" + getBookMoreThanCountInStocks() + "' AND ");
        }
        if (getBookLessThanCountInStocks() != null) {
            sb.append("count_in_stock ");
            sb.append("<='" + getBookLessThanCountInStocks() + "' AND ");
        }
        if (getBookCountInStocks() != null) {
            sb.append("count_in_stock='" + getBookCountInStocks() + "' AND ");
        }

        sb.delete(sb.length() - 4, sb.length());
        sb.append(";");
        return sb.toString();
    }
}
