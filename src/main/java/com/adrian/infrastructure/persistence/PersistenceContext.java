// src/main/java/com/adrian/infrastructure/persistence/PersistenceContext.java
package com.adrian.infrastructure.persistence;



import com.adrian.domain.entities.*;
import com.adrian.infrastructure.persistence.dao.*;
import com.adrian.infrastructure.persistence.exception.DatabaseAccessException;
import com.adrian.infrastructure.persistence.util.ConnectionHolder;
import com.adrian.infrastructure.persistence.util.ConnectionManager;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Unit of Work: збирає всі зміни (створення, оновлення, видалення)
 * і застосовує їх в одній транзакції через єдине Connection.
 */
@Component
public class PersistenceContext {

    private final MovieDao movieDao;
    private final SessionDao sessionDao;
    private final SeatDao seatDao;
    private final TicketDao ticketDao;
    private final FoodItemDao foodItemDao;
    private final FoodItemTranslationDao foodItemTranslationDao;
    private final OrderDao orderDao;
    private final OrderFoodItemDao orderFoodItemDao;
    private final PaymentMethodDao paymentMethodDao;
    private final PaymentDao paymentDao;
    private final PromotionDao promotionDao;

    private Connection connection;
    private final List<Object> newEntities     = new ArrayList<>();
    private final Map<Object,Object> updates   = new LinkedHashMap<>();
    private final List<Object> deletedEntities = new ArrayList<>();
    private final Map<Class<?>,Object> repoMap  = new HashMap<>();

    public PersistenceContext(
        MovieDao movieDao,
        SessionDao sessionDao,
        SeatDao seatDao,
        TicketDao ticketDao,
        FoodItemDao foodItemDao,
        FoodItemTranslationDao foodItemTranslationDao,
        OrderDao orderDao,
        OrderFoodItemDao orderFoodItemDao,
        PaymentMethodDao paymentMethodDao,
        PaymentDao paymentDao,
        PromotionDao promotionDao
    ) {
        this.movieDao  = movieDao;
        this.sessionDao= sessionDao;
        this.seatDao   = seatDao;
        this.ticketDao = ticketDao;
        this.foodItemDao = foodItemDao;
        this.foodItemTranslationDao = foodItemTranslationDao;
        this.orderDao  = orderDao;
        this.orderFoodItemDao = orderFoodItemDao;
        this.paymentMethodDao = paymentMethodDao;
        this.paymentDao = paymentDao;
        this.promotionDao = promotionDao;
        initializeConnection();
    }

    @PostConstruct
    private void init() {
        repoMap.put(Movie.class, movieDao);
        repoMap.put(Session.class, sessionDao);
        repoMap.put(Seat.class, seatDao);
        repoMap.put(Ticket.class, ticketDao);
        repoMap.put(FoodItem.class, foodItemDao);
        repoMap.put(FoodItemTranslation.class, foodItemTranslationDao);
        repoMap.put(Order.class, orderDao);
        repoMap.put(com.adrian.domain.entities.OrderFoodItem.class, orderFoodItemDao);
        repoMap.put(PaymentMethod.class, paymentMethodDao);
        repoMap.put(Payment.class, paymentDao);
        repoMap.put(Promotion.class, promotionDao);
    }

    private void initializeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            connection = ConnectionManager.get();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DatabaseAccessException("Помилка ініціалізації з'єднання", e);
        }
    }

    public void registerNew(Object entity) {
        newEntities.add(entity);
    }

    public void registerDirty(Object id, Object entity) {
        updates.put(id, entity);
    }

    public void registerDeleted(Object entity) {
        deletedEntities.add(entity);
    }

    @SuppressWarnings("unchecked")
    private <T,ID> GenericDao<T,ID> getDao(Class<?> cls) {
        Object dao = repoMap.get(cls);
        if (dao == null) {
            throw new IllegalStateException("Репозиторій для " + cls.getSimpleName() + " не зареєстровано");
        }
        return (GenericDao<T,ID>) dao;
    }

    private Object extractId(Object entity) {
        try {
            return entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            throw new DatabaseAccessException("Не вдалося отримати ID сутності", e);
        }
    }

    public void commit() {
        // 1) Відкриваємо єдине з’єднання та вимикаємо auto-commit
        initializeConnection();

        // 2) Встановлюємо це з’єднання у ThreadLocal, щоб DAO могли його отримати
        ConnectionHolder.setConnection(connection);

        try {
            // 3) INSERT нових сутностей
            for (Object e : newEntities) {
                getDao(e.getClass()).save(e);
            }
            // 4) UPDATE змінених
            for (Map.Entry<Object,Object> en : updates.entrySet()) {
                getDao(en.getValue().getClass()).save(en.getValue());
            }
            // 5) DELETE позначених
            for (Object e : deletedEntities) {
                Object id = extractId(e);
                getDao(e.getClass()).deleteById(id);
            }

            // 6) Коміт транзакції
            connection.commit();
        } catch (Exception ex) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {}
            throw new DatabaseAccessException("Помилка виконання транзакції", ex);
        } finally {
            // 7) Очищаємо реєстри
            newEntities.clear();
            updates.clear();
            deletedEntities.clear();
            // 8) Очищаємо ThreadLocal
            ConnectionHolder.clearConnection();
            // 9) Закриваємо з’єднання
            try {
                connection.close();
            } catch (SQLException ignore) {}
        }
    }

}
