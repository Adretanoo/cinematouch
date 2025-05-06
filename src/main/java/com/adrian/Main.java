package com.adrian;
import com.adrian.domain.entities.Promotion;
import com.adrian.infrastructure.persistence.dao.PromotionDao;
import com.adrian.infrastructure.persistence.dao.impl.PromotionDaoImpl;
import com.adrian.infrastructure.persistence.util.ConnectionHolder;
import com.adrian.infrastructure.persistence.util.ConnectionManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class Main {
    public static void main(String[] args) {
        ConnectionHolder.setConnection(ConnectionManager.get());
        try {
            PromotionDao dao = new PromotionDaoImpl();

            // 1) Створюємо нову акцію
            Promotion promo = new Promotion();
            promo.setName("Summer Blast");
            promo.setDiscountPercent(new BigDecimal("25.00"));
            promo.setStartDate(LocalDate.now());
            promo.setEndDate(LocalDate.now().plusWeeks(2));
            promo.setDescription("Спеціальна літня знижка 25% на всі товари!");
            promo.setImagePath("/images/promos/summer_blast.png");

            dao.save(promo);
            System.out.println("Inserted Promotion id = " + promo.getId());

            // 2) Читаємо за id
            Optional<Promotion> found = dao.findById(promo.getId());
            found.ifPresent(p -> System.out.println("Found: " + p));

            // 3) Виводимо всі записи
            List<Promotion> all = dao.findAll();
            System.out.println("All promotions:");
            all.forEach(System.out::println);

            // 4) Оновлюємо опис і шлях до зображення
            promo.setDescription("Updated description for Summer Blast!");
            promo.setImagePath("/images/promos/summer_blast_v2.png");
            dao.save(promo);
            System.out.println("Updated Promotion: " + promo);


        } finally {
            ConnectionHolder.clearConnection();
            ConnectionManager.closePool();
        }
    }
}
