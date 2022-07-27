package controler.FindRequests;

import lombok.Data;

import java.util.UUID;

@Data
public class FindCartRequest {
    private UUID cartId;
    private UUID personId;
    private String cartname;

    public FindCartRequest setCartCartId(UUID cartId) {
        setCartId(cartId);
        return this;
    }

    public FindCartRequest setCartPersonId(UUID personId) {
        setPersonId(personId);
        return this;
    }

    public FindCartRequest setCartCartname(String cartname) {
        setCartname(cartname);
        return this;
    }
}
