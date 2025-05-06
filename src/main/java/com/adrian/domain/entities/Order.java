package com.adrian.domain.entities;

import com.adrian.domain.enums.OrderPaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ticket_id", nullable = false)
    private Long ticketId;

    @NotNull
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private OrderPaymentStatus paymentStatus;

    /**
     * Масив food_item_ids — Hibernate не мапить це поле на колонку,
     * воно обробляється вручну в DAO.
     */
    @Transient
    private List<Long> foodItemIds;
}