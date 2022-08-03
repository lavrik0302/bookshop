package com.intexsoft.controler.findRequest;

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

}
