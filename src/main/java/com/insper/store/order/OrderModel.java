package com.insper.store.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "orders")
@EqualsAndHashCode(of = "id")
@Builder @Getter @Setter @Accessors(chain = true, fluent = true)
@NoArgsConstructor @AllArgsConstructor
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_order")
    private String id;

    @Column(name = "id_product")
    private Integer productId;

    @Column(name = "quantity_order")
    private Integer quantity;

    @Column(name = "email_order")
    private String email;

    public OrderModel(OrderIn o) {
        this.productId = o.productId();
        this.quantity = o.quantity();
        this.email = o.email();
    }

    public OrderOut to() {
        return OrderOut.builder()
            .id(id)
            .build();
    }

    public void update(OrderIn o) {
        this.productId = o.productId();
        this.quantity = o.quantity();
        this.email = o.email();
    }
    
}