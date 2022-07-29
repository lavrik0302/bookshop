package controler.findRequest.toStringSqlStatement;

import controler.findRequest.FindCartRequest;

public class ToSqlStringStatementForCart {
    public String toSQLStringStatement(FindCartRequest findCartRequest){
        StringBuilder sb =new StringBuilder();
        if (findCartRequest.getCartId() != null) {
            sb.append("cart_id ");
            sb.append("='").append(findCartRequest.getCartId()).append("' AND ");
        }
        if (findCartRequest.getPersonId() != null) {
            sb.append("person_id ");
            sb.append("='").append(findCartRequest.getPersonId()).append("' AND ");
        }
        if (findCartRequest.getCartname() != null) {
            sb.append("cart_name ");
            sb.append("='").append(findCartRequest.getCartname()).append("' AND ");
        }
        sb.delete(sb.length() - 4, sb.length());
        sb.append(";");
        return sb.toString();
    }
}
