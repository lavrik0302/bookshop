package controler.UpdateRequests;

import controler.FindRequests.FindCartRequest;
import lombok.Data;

import java.util.UUID;
@Data
public class UpdateCartRequest {
    private UUID cartId;
    private UUID personId;
    private String cartname;

    public UpdateCartRequest setCartCartId(UUID cartId) {
        setCartId(cartId);
        return this;
    }

    public UpdateCartRequest setCartPersonId(UUID personId) {
        setPersonId(personId);
        return this;
    }

    public UpdateCartRequest setCartCartname(String cartname) {
        setCartname(cartname);
        return this;
    }
}
