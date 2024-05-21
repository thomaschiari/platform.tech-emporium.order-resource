package com.insper.store.order;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import lombok.NonNull;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @CachePut(value = "orders", key = "#result.id")
    public OrderModel create(OrderIn in) {
        return orderRepository.save(new OrderModel(in));
    }

    @Cacheable(value = "orders", key = "#id")
    public Optional<OrderModel> read(@NonNull String id) {
        return orderRepository.findById(id);
    }

    @CachePut(value = "orders", key = "#id")
    public Optional<OrderModel> update(@NonNull String id, OrderIn in) {
        return orderRepository.findById(id).map(order -> {
            order.productId(in.productId());
            order.quantity(in.quantity());
            order.email(in.email());
            return orderRepository.save(order);
        });
    }

    @CacheEvict(value = "orders", key = "#id")
    public void delete(@NonNull String id) {
        orderRepository.deleteById(id);
    }
}
