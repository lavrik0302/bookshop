package controler.updateRequests;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateCartHasBookRequest {
    private UUID cartIds;
    private UUID bookIds;
    private Integer bookCounts;

    public UpdateCartHasBookRequest setCartId(UUID cartId) {
        setCartIds(cartId);
        return this;
    }

    public UpdateCartHasBookRequest setBookId(UUID bookId) {
        setBookIds(bookId);
        return this;
    }

    public UpdateCartHasBookRequest setBookCount(Integer bookCount) {
        setBookCounts(bookCount);
        return this;
    }

}
