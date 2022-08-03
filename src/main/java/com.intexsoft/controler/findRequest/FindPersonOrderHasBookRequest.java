package com.intexsoft.controler.findRequest;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Data
public class FindPersonOrderHasBookRequest {
    private List<UUID> orderIds = new ArrayList<>();
    private List<UUID> bookIds = new ArrayList<>();
    private List<Integer> bookCounts=new ArrayList<>();

    public FindPersonOrderHasBookRequest setOrderId(UUID cartId) {
        orderIds.add(cartId);
        return this;
    }

    public FindPersonOrderHasBookRequest setOrderId(List<UUID> cartId) {
        orderIds.addAll(cartId);
        return this;
    }

    public FindPersonOrderHasBookRequest setBookId(UUID bookId) {
        bookIds.add(bookId);
        return this;
    }

    public FindPersonOrderHasBookRequest setBookId(List<UUID> bookId) {
        bookIds.addAll(bookId);
        return this;
    }

    public FindPersonOrderHasBookRequest setBookCount(Integer bookCount) {
        bookCounts.add(bookCount);
        return this;
    }

    public FindPersonOrderHasBookRequest setBookCount(List<Integer> bookCount) {
        bookCounts.addAll(bookCount);
        return this;
    }


}
