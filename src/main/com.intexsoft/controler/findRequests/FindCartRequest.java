package controler.findRequests;

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
    public String toSQLStringStatement(){
        StringBuilder sb =new StringBuilder();
        if (getCartId() != null) {
            sb.append("cart_id ");
            sb.append("='" + getCartId() + "' AND ");
        }
        if (getPersonId() != null) {
            sb.append("person_id ");
            sb.append("='" + getPersonId() + "' AND ");
        }
        if (getCartname() != null) {
            sb.append("cart_name ");
            sb.append("='" + getCartname() + "' AND ");
        }
        sb.delete(sb.length() - 4, sb.length());
        sb.append(";");
        return sb.toString();
    }
}
