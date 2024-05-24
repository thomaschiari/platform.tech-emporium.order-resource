package com.insper.store.order;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import lombok.NonNull;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @CircuitBreaker(name = "order", fallbackMethod = "createFallback")
    @CachePut(value = "orders", key = "#result.id")
    public OrderModel create(OrderIn in) {
        return orderRepository.save(new OrderModel(in));
    }

    public OrderModel createFallback(OrderIn in, Throwable t) {
        throw new RuntimeException("Failed to create order", t);
    }

    @CircuitBreaker(name = "order", fallbackMethod = "readFallback")
    @Cacheable(value = "orders", key = "#id")
    public Optional<OrderModel> read(@NonNull String id) {
        return orderRepository.findById(id);
    }

    public Optional<OrderModel> readFallback(String id, Throwable t) {
        throw new RuntimeException("Failed to read order", t);
    }

    @CircuitBreaker(name = "order", fallbackMethod = "updateFallback")
    @CachePut(value = "orders", key = "#id")
    public Optional<OrderModel> update(@NonNull String id, OrderIn in) {
        return orderRepository.findById(id).map(order -> {
            order.productId(in.productId());
            order.quantity(in.quantity());
            order.email(in.email());
            return orderRepository.save(order);
        });
    }

    public Optional<OrderModel> updateFallback(String id, OrderIn in, Throwable t) {
        throw new RuntimeException("Failed to update order", t);
    }

    @CircuitBreaker(name = "order", fallbackMethod = "deleteFallback")
    @CacheEvict(value = "orders", key = "#id")
    public void delete(@NonNull String id) {
        orderRepository.deleteById(id);
    }

    public void deleteFallback(String id, Throwable t) {
        throw new RuntimeException("Failed to delete order", t);
    }
}
