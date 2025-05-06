package com.adrian.infrastructure.persistence.dao;

import com.adrian.domain.entities.OrderFoodItem;

import java.util.List;
import java.util.Optional;

/**
 * DAO для таблиці order_food_items.
 */
public interface OrderFoodItemDao {
    void insert(OrderFoodItem ofi);
    void update(OrderFoodItem ofi);
    void delete(Long orderId, Long foodItemId);
    Optional<OrderFoodItem> find(Long orderId, Long foodItemId);
    List<OrderFoodItem> findByOrderId(Long orderId);
    List<OrderFoodItem> findAll();
}
