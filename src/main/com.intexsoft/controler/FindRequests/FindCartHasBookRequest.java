package controler.FindRequests;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class FindCartHasBookRequest {
    private List<UUID> cartIds = new ArrayList<>();
    private List<UUID> bookIds = new ArrayList<>();
    private List<Integer> bookCounts=new ArrayList<>();

    public FindCartHasBookRequest setCartId(UUID cartId) {
        cartIds.add(cartId);
        return this;
    }

    public FindCartHasBookRequest setCartId(List<UUID> cartId) {
        cartIds.addAll(cartId);
        return this;
    }

    public FindCartHasBookRequest setBookId(UUID bookId) {
        bookIds.add(bookId);
        return this;
    }

    public FindCartHasBookRequest setBookId(List<UUID> bookId) {
        bookIds.addAll(bookId);
        return this;
    }

    public FindCartHasBookRequest setBookCount(Integer bookCount) {
        bookCounts.add(bookCount);
        return this;
    }

    public FindCartHasBookRequest setBookCount(List<Integer> bookCount) {
        bookCounts.addAll(bookCount);
        return this;
    }

    public String toSQLStringStatement() {
        StringBuilder sb = new StringBuilder();
        if (!getCartIds().isEmpty()) {
            sb.append("cart_id ");
            if (getCartIds().size() > 1) {
                sb.append("in (");
                for (UUID cart_id : getCartIds()) {
                    sb.append("'" + cart_id + "', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='" + getCartIds().get(0) + "' AND ");
            }
        }
        if (!getBookIds().isEmpty()) {
            sb.append("book_id ");
            if (getBookIds().size() > 1) {
                sb.append("in (");
                for (UUID bookId : getBookIds()) {
                    sb.append("'" + bookId + "', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='" + getBookIds().get(0) + "' AND ");
            }
        }
        if (!getBookCounts().isEmpty()) {
            sb.append("book_count ");
            if (getBookCounts().size() > 1) {
                sb.append("in (");
                for (Integer bookCount : getBookCounts()) {
                    sb.append("'" + bookCount + "', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='" + getBookCounts().get(0) + "' AND ");
            }
        }
        sb.delete(sb.length() - 4, sb.length());
        sb.append(";");
        return sb.toString();
    }
}
