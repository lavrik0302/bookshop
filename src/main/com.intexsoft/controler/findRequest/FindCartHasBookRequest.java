package controler.findRequest;

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


}
