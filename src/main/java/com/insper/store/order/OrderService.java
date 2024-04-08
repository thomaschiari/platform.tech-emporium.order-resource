package com.insper.store.order;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderModel create(Order in) {
        return orderRepository.save(new OrderModel(in));
    }

    public Order read(@NonNull String id) {
        return orderRepository.findById(id).map(OrderModel::to).orElse(null);
    }

    public Order update(@NonNull String id, Order in) {
        OrderModel model = orderRepository.findById(id).orElseThrow();
        model.update(in);
        return orderRepository.save(model).to();
    }

    public void delete(@NonNull String id) {
        orderRepository.deleteById(id);
    }

}