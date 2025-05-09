package com.adrian.infrastructure.persistence;

import com.adrian.domain.entities.*;
import com.adrian.infrastructure.persistence.dao.*;
import com.adrian.infrastructure.persistence.exception.DatabaseAccessException;
import com.adrian.infrastructure.persistence.util.ConnectionHolder;
import com.adrian.infrastructure.persistence.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

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

        initializeConnection();
    }

    private void initializeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            connection = ConnectionManager.get();
            connection.setAutoCommit(false);
            ConnectionHolder.setConnection(connection);
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
        initializeConnection();

        try {
            for (Object e : newEntities) {
                getDao(e.getClass()).save(e);
            }
            for (var en : updates.entrySet()) {
                getDao(en.getValue().getClass()).save(en.getValue());
            }
            for (Object e : deletedEntities) {
                Object id = extractId(e);
                getDao(e.getClass()).deleteById(id);
            }
            connection.commit();
        } catch (Exception ex) {
            try { connection.rollback(); } catch (SQLException ignore) {}
            throw new DatabaseAccessException("Помилка виконання транзакції", ex);
        } finally {
            newEntities.clear();
            updates.clear();
            deletedEntities.clear();
            ConnectionHolder.clearConnection();
            try { connection.close(); } catch (SQLException ignore) {}
            // Відразу відновлюємо для наступних звернень
            initializeConnection();
        }
    }

    // Гетери для DAO
    public MovieDao getMovieDao() { return movieDao; }
    public SessionDao getSessionDao() { return sessionDao; }
    public SeatDao getSeatDao() { return seatDao; }
    public TicketDao getTicketDao() { return ticketDao; }
    public FoodItemDao getFoodItemDao() { return foodItemDao; }
    public FoodItemTranslationDao getFoodItemTranslationDao() { return foodItemTranslationDao; }
    public OrderDao getOrderDao() { return orderDao; }
    public OrderFoodItemDao getOrderFoodItemDao() { return orderFoodItemDao; }
    public PaymentMethodDao getPaymentMethodDao() { return paymentMethodDao; }
    public PaymentDao getPaymentDao() { return paymentDao; }
    public PromotionDao getPromotionDao() { return promotionDao; }
}
