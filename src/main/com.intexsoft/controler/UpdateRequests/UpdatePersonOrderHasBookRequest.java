package controler.UpdateRequests;

import lombok.Data;

import java.util.UUID;
@Data
public class UpdatePersonOrderHasBookRequest {
    private UUID orderIds;
    private UUID bookIds;
    private Integer bookCounts;

    public UpdatePersonOrderHasBookRequest setOrderId(UUID cartId) {
        setOrderIds(cartId);
        return this;
    }

    public UpdatePersonOrderHasBookRequest setBookId(UUID bookId) {
        setBookIds(bookId);
        return this;
    }

    public UpdatePersonOrderHasBookRequest setBookCount(Integer bookCount) {
        setBookCounts(bookCount);
        return this;
    }
}
