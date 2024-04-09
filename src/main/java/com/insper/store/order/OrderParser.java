package com.insper.store.order;

class OrderParser {

    public static Order to(OrderIn in) {
        return Order.builder()
            .productId(in.productId())
            .quantity(in.quantity())
            .email(in.email())
            .build();
    }

    public static OrderOut to(Order order) {
        return OrderOut.builder()
            .id(order.id())
            .build();
    }
    
}
