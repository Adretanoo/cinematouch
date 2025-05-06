package com.adrian.domain.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFoodItem {
    private Long orderId;
    private Long foodItemId;
    private int quantity;
    private BigDecimal subtotal;
}
