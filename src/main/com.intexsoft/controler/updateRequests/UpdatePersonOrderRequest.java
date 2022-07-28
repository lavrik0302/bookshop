package controler.updateRequests;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdatePersonOrderRequest {
    private UUID orderIds;
    private UUID personIds;
    private String adresses;
    private Integer statusIds;

    public UpdatePersonOrderRequest setOrderId(UUID orderId) {
        setOrderIds(orderId);
        return this;
    }

    public UpdatePersonOrderRequest setPersonId(UUID personId) {
        setPersonIds(personId);
        return this;
    }
    public UpdatePersonOrderRequest setAdress(String adress) {
        setAdresses(adress);
        return this;
    }
    public UpdatePersonOrderRequest setStatusId(int statusId) {
        setStatusIds(statusId);
        return this;
    }
}
