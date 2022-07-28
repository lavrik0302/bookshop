package controler.findRequests;

import lombok.Data;

import java.util.UUID;

@Data
public class FindPersonOrderRequest {
    private UUID orderIds;
    private UUID personIds;
    private String adresses;
    private Integer statusIds;

    public FindPersonOrderRequest setOrderId(UUID orderId) {
        setOrderIds(orderId);
        return this;
    }

    public FindPersonOrderRequest setPersonId(UUID personId) {
        setPersonIds(personId);
        return this;
    }
    public FindPersonOrderRequest setAdress(String adress) {
        setAdresses(adress);
        return this;
    }
    public FindPersonOrderRequest setStatusId(int statusId) {
        setStatusIds(statusId);
        return this;
    }
    public String toSQLStringStatement(){
        StringBuilder sb =new StringBuilder();
        if (getOrderIds() != null) {
            sb.append("po.order_id ");
            sb.append("='" + getOrderIds() + "' AND ");
        }
        if (getPersonIds() != null) {
            sb.append("person_id ");
            sb.append("='" + getPersonIds() + "' AND ");
        }
        if (getAdresses() != null) {
            sb.append("adress ");
            sb.append("='" + getAdresses() + "' AND ");
        }
        if (getStatusIds() != null) {
            sb.append("status_id ");
            sb.append("='" + getStatusIds() + "' AND ");
        }
        sb.delete(sb.length() - 4, sb.length());
        sb.append(";");
        return sb.toString();
    }
}
