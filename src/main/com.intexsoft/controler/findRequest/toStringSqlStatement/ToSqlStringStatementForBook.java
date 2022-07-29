package controler.findRequest.toStringSqlStatement;

import controler.findRequest.FindBookRequest;

import java.util.UUID;

public class ToSqlStringStatementForBook {
    public String toSQLStringStatement(FindBookRequest findBookRequest) {
        StringBuilder sb = new StringBuilder();
        if (!findBookRequest.getBookIds().isEmpty()) {
            sb.append("book_id ");
            if (findBookRequest.getBookIds().size() > 1) {
                sb.append("in (");
                for (UUID book_id : findBookRequest.getBookIds()) {
                    sb.append("'").append(book_id).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findBookRequest.getBookIds().get(0)).append("' AND ");
            }
        }
        if (!findBookRequest.getBookBooknames().isEmpty()) {
            sb.append("bookname ");
            if (findBookRequest.getBookBooknames().size() > 1) {
                sb.append("in (");
                for (String bookname : findBookRequest.getBookBooknames()) {
                    sb.append("'").append(bookname).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findBookRequest.getBookBooknames().get(0)).append("' AND ");
            }
        }
        if (!findBookRequest.getBookAuthors().isEmpty()) {
            sb.append("author ");
            if (findBookRequest.getBookAuthors().size() > 1) {
                sb.append("in (");
                for (String author : findBookRequest.getBookAuthors()) {
                    sb.append("'").append(author).append("', ");
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(") AND ");
            } else {
                sb.append("='").append(findBookRequest.getBookAuthors().get(0)).append("' AND ");
            }
        }
        if (findBookRequest.getBookMoreThanCostInByns() != null) {
            sb.append("cost_in_byn ");
            sb.append(">='").append(findBookRequest.getBookMoreThanCostInByns()).append("' AND ");
        }
        if (findBookRequest.getBookLessThanCostInByns() != null) {
            sb.append("cost_in_byn ");
            sb.append("<='").append(findBookRequest.getBookLessThanCostInByns()).append("' AND ");
        }
        if (findBookRequest.getBookCostInByns() != null) {
            sb.append("cost_in_byn='").append(findBookRequest.getBookCostInByns()).append("' AND ");
        }

        if (findBookRequest.getBookMoreThanCountInStocks() != null) {
            sb.append("count_in_stock ");
            sb.append(">='").append(findBookRequest.getBookMoreThanCountInStocks()).append("' AND ");
        }
        if (findBookRequest.getBookLessThanCountInStocks() != null) {
            sb.append("count_in_stock ");
            sb.append("<='").append(findBookRequest.getBookLessThanCountInStocks()).append("' AND ");
        }
        if (findBookRequest.getBookCountInStocks() != null) {
            sb.append("count_in_stock='").append(findBookRequest.getBookCountInStocks()).append("' AND ");
        }

        sb.delete(sb.length() - 4, sb.length());
        sb.append(";");
        return sb.toString();
    }
}
