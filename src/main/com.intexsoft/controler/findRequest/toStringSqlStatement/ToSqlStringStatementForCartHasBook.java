package controler.findRequest.toStringSqlStatement;

import controler.findRequest.FindCartHasBookRequest;

import java.util.UUID;

public class ToSqlStringStatementForCartHasBook {
    public String toSQLStringStatement(FindCartHasBookRequest findCartHasBookRequest) {
        StringBuilder sb = new StringBuilder();
        if (!findCartHasBookRequest.getCartIds().isEmpty()) {
            sb.append("cart_id ");
            if (findCartHasBookRequest.getCartIds().size() > 1) {
                sb.append("in (");
                for (UUID cart_id : findCartHasBookRequest.getCartIds()) {
                    sb.append("'").append(cart_id).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findCartHasBookRequest.getCartIds().get(0)).append("' AND ");
            }
        }
        if (!findCartHasBookRequest.getBookIds().isEmpty()) {
            sb.append("book_id ");
            if (findCartHasBookRequest.getBookIds().size() > 1) {
                sb.append("in (");
                for (UUID bookId : findCartHasBookRequest.getBookIds()) {
                    sb.append("'").append(bookId).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findCartHasBookRequest.getBookIds().get(0)).append("' AND ");
            }
        }
        if (!findCartHasBookRequest.getBookCounts().isEmpty()) {
            sb.append("book_count ");
            if (findCartHasBookRequest.getBookCounts().size() > 1) {
                sb.append("in (");
                for (Integer bookCount : findCartHasBookRequest.getBookCounts()) {
                    sb.append("'").append(bookCount).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findCartHasBookRequest.getBookCounts().get(0)).append("' AND ");
            }
        }
        sb.delete(sb.length() - 4, sb.length());
        sb.append(";");
        return sb.toString();
    }
}
