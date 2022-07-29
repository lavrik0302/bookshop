package controler.findRequest.toStringSqlStatement;

import controler.findRequest.FindPersonOrderHasBookRequest;

import java.util.UUID;

public class ToSqlStringStatementForPersonOrderHasBook {
    public String toSQLStringStatement(FindPersonOrderHasBookRequest findPersonOrderHasBookRequest) {
        StringBuilder sb = new StringBuilder();
        if (!findPersonOrderHasBookRequest.getOrderIds().isEmpty()) {
            sb.append("order_id ");
            if (findPersonOrderHasBookRequest.getOrderIds().size() > 1) {
                sb.append("in (");
                for (UUID cart_id : findPersonOrderHasBookRequest.getOrderIds()) {
                    sb.append("'").append(cart_id).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findPersonOrderHasBookRequest.getOrderIds().get(0)).append("' AND ");
            }
        }
        if (!findPersonOrderHasBookRequest.getBookIds().isEmpty()) {
            sb.append("book_id ");
            if (findPersonOrderHasBookRequest.getBookIds().size() > 1) {
                sb.append("in (");
                for (UUID bookId : findPersonOrderHasBookRequest.getBookIds()) {
                    sb.append("'").append(bookId).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findPersonOrderHasBookRequest.getBookIds().get(0)).append("' AND ");
            }
        }
        if (!findPersonOrderHasBookRequest.getBookCounts().isEmpty()) {
            sb.append("book_count ");
            if (findPersonOrderHasBookRequest.getBookCounts().size() > 1) {
                sb.append("in (");
                for (Integer bookCount : findPersonOrderHasBookRequest.getBookCounts()) {
                    sb.append("'").append(bookCount).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findPersonOrderHasBookRequest.getBookCounts().get(0)).append("' AND ");
            }
        }
        sb.delete(sb.length() - 4, sb.length());
        sb.append(";");
        return sb.toString();
    }
}
