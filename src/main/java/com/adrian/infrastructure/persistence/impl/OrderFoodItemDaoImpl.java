package com.adrian.infrastructure.persistence.dao.impl;


import com.adrian.domain.entities.OrderFoodItem;
import com.adrian.infrastructure.persistence.dao.OrderFoodItemDao;
import com.adrian.infrastructure.persistence.util.ConnectionHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderFoodItemDaoImpl implements OrderFoodItemDao {
    @Override
    public void insert(OrderFoodItem ofi) {
        String sql = """
            INSERT INTO order_food_items
              (order_id, food_item_id, quantity, subtotal)
            VALUES (?, ?, ?, ?)
            """;
        Connection conn = ConnectionHolder.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, ofi.getOrderId());
            ps.setLong(2, ofi.getFoodItemId());
            ps.setInt(3, ofi.getQuantity());
            ps.setBigDecimal(4, ofi.getSubtotal());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Insert failed in order_food_items", e);
        }
    }

    @Override
    public void update(OrderFoodItem ofi) {
        String sql = """
            UPDATE order_food_items
               SET quantity = ?, subtotal = ?
             WHERE order_id = ? AND food_item_id = ?
            """;
        Connection conn = ConnectionHolder.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ofi.getQuantity());
            ps.setBigDecimal(2, ofi.getSubtotal());
            ps.setLong(3, ofi.getOrderId());
            ps.setLong(4, ofi.getFoodItemId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Update failed in order_food_items", e);
        }
    }

    @Override
    public void delete(Long orderId, Long foodItemId) {
        String sql = "DELETE FROM order_food_items WHERE order_id = ? AND food_item_id = ?";
        Connection conn = ConnectionHolder.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orderId);
            ps.setLong(2, foodItemId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Delete failed in order_food_items", e);
        }
    }

    @Override
    public Optional<OrderFoodItem> find(Long orderId, Long foodItemId) {
        String sql = "SELECT * FROM order_food_items WHERE order_id = ? AND food_item_id = ?";
        Connection conn = ConnectionHolder.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orderId);
            ps.setLong(2, foodItemId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Find failed in order_food_items", e);
        }
    }

    @Override
    public List<OrderFoodItem> findByOrderId(Long orderId) {
        String sql = "SELECT * FROM order_food_items WHERE order_id = ?";
        Connection conn = ConnectionHolder.getConnection();
        List<OrderFoodItem> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Find by order_id failed in order_food_items", e);
        }
    }

    @Override
    public List<OrderFoodItem> findAll() {
        String sql = "SELECT * FROM order_food_items";
        Connection conn = ConnectionHolder.getConnection();
        List<OrderFoodItem> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Find all failed in order_food_items", e);
        }
    }

    private OrderFoodItem mapRow(ResultSet rs) throws SQLException {
        return new OrderFoodItem(
            rs.getLong("order_id"),
            rs.getLong("food_item_id"),
            rs.getInt("quantity"),
            rs.getBigDecimal("subtotal")
        );
    }
}

