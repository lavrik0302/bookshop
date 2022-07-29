package controler.findRequest.toStringSqlStatement;

import controler.findRequest.FindPersonOrderRequest;

public class ToSqlStringStatementForPersonOrder {
    public String toSQLStringStatement(FindPersonOrderRequest findPersonOrderRequest){
        StringBuilder sb =new StringBuilder();
        if (findPersonOrderRequest.getOrderIds() != null) {
            sb.append("po.order_id ");
            sb.append("='").append(findPersonOrderRequest.getOrderIds()).append("' AND ");
        }
        if (findPersonOrderRequest.getPersonIds() != null) {
            sb.append("person_id ");
            sb.append("='").append(findPersonOrderRequest.getPersonIds()).append("' AND ");
        }
        if (findPersonOrderRequest.getAdresses() != null) {
            sb.append("adress ");
            sb.append("='").append(findPersonOrderRequest.getAdresses()).append("' AND ");
        }
        if (findPersonOrderRequest.getStatusIds() != null) {
            sb.append("status_id ");
            sb.append("='").append(findPersonOrderRequest.getStatusIds()).append("' AND ");
        }
        sb.delete(sb.length() - 4, sb.length());
        sb.append(";");
        return sb.toString();
    }
}
