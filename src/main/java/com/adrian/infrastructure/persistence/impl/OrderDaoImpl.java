package com.adrian.infrastructure.persistence.impl;

import com.adrian.domain.entities.Order;
import com.adrian.domain.enums.OrderPaymentStatus;
import com.adrian.infrastructure.persistence.dao.OrderDao;

import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl extends AbstractJdbcDao<Order, Long> implements OrderDao {

    @Override
    protected String getTableName() {
        return "orders";
    }

    @Override
    protected String getIdColumn() {
        return "id";
    }

    // Тепер включаємо food_item_ids
    @Override
    protected String getInsertColumns() {
        return "ticket_id, total_price, payment_status, food_item_ids";
    }

    @Override
    protected String getInsertPlaceholders() {
        return "?, ?, ?, ?";
    }

    @Override
    protected String getUpdateAssignments() {
        return "ticket_id = ?, total_price = ?, payment_status = ?, food_item_ids = ?";
    }

    @Override
    protected int getUpdateParameterCount() {
        return 4;
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Order o) throws SQLException {
        ps.setLong(1, o.getTicketId());
        ps.setBigDecimal(2, o.getTotalPrice());
        ps.setObject(3, o.getPaymentStatus().name(), Types.OTHER);

        // массив Long → Array
        List<Long> ids = o.getFoodItemIds();
        if (ids == null) {
            ps.setNull(4, Types.ARRAY);
        } else {
            // драйвер PostgreSQL потребує Object[]
            Object[] arr = ids.toArray(new Object[0]);
            Array sqlArr = ps.getConnection().createArrayOf("INT8", arr);
            ps.setArray(4, sqlArr);
        }
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Order o) throws SQLException {
        setInsertParameters(ps, o);
    }

    @Override
    protected Order mapRow(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setId(rs.getLong("id"));
        o.setTicketId(rs.getLong("ticket_id"));
        o.setTotalPrice(rs.getBigDecimal("total_price"));
        o.setPaymentStatus(OrderPaymentStatus.valueOf(rs.getString("payment_status")));

        Array sqlArray = rs.getArray("food_item_ids");
        if (sqlArray != null) {
            Object[] raw = (Object[]) sqlArray.getArray();
            List<Long> ids = Arrays.stream(raw)
                .map(elem -> elem == null ? null : ((Number) elem).longValue())
                .collect(Collectors.toList());
            o.setFoodItemIds(ids);
        } else {
            o.setFoodItemIds(Collections.emptyList());
        }

        return o;
    }


    @Override
    protected Long getEntityId(Order o) {
        return o.getId();
    }

    @Override
    protected void setEntityId(Order o, Long id) {
        o.setId(id);
    }
}