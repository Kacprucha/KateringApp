package com.kateringapp.backend.services;

import com.kateringapp.backend.configurations.SpringContextRetriever;
import com.kateringapp.backend.dtos.MealGetDTO;
import com.kateringapp.backend.dtos.MealStatisticsDTO;
import com.kateringapp.backend.dtos.OrderStatisticsDTO;
import com.kateringapp.backend.dtos.criteria.OrderStatisticCriteria;
import com.kateringapp.backend.entities.QMeal;
import com.kateringapp.backend.entities.order.OrderStatus;
import com.kateringapp.backend.entities.order.QOrder;
import com.kateringapp.backend.services.interfaces.IStatistics;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.querydsl.core.types.dsl.Expressions.dateTemplate;

@Service
@RequiredArgsConstructor
public class StatisticsService implements IStatistics {

    @PersistenceContext
    private final EntityManager entityManager;
    private final SpringContextRetriever springContextRetriever;

    @Override
    public List<OrderStatisticsDTO> getOrderStatistics(OrderStatisticCriteria criteria) {
        QOrder qOrder = QOrder.order;
        QMeal qMeal = QMeal.meal;
        UUID cateringFirmId = springContextRetriever.getCurrentUserIdFromJwt();

        BooleanBuilder conditions = new BooleanBuilder();
        conditions.and(qMeal.cateringFirmData.cateringFirmId.eq(cateringFirmId))
                .and(qOrder.orderStatus.eq(OrderStatus.COMPLETED));

        if(criteria.getStatisticsPeriod() == null) {
            if (criteria.getStartDate() != null) {
                conditions.and(qOrder.completedAt.goe(criteria.getStartDate()));
            }
            if (criteria.getEndDate() != null) {
                conditions.and(qOrder.completedAt.loe(criteria.getEndDate()));
            }
        }
        else{
            switch(criteria.getStatisticsPeriod()){
                case WEEK -> {
                    Instant oneWeekAgo = LocalDateTime.now().minusWeeks(1)
                            .atZone(ZoneId.systemDefault())
                            .toInstant();
                    conditions.and(qOrder.completedAt.goe(Timestamp.from(oneWeekAgo)));
                }
                case MONTH -> {
                    Instant oneMonthAgo = LocalDateTime.now().minusMonths(1)
                            .atZone(ZoneId.systemDefault())
                            .toInstant();
                    conditions.and(qOrder.completedAt.goe(Timestamp.from(oneMonthAgo)));
                }
                case YEAR -> {
                    Instant oneYearAgo = LocalDateTime.now().minusYears(1)
                            .atZone(ZoneId.systemDefault())
                            .toInstant();
                    conditions.and(qOrder.completedAt.goe(Timestamp.from(oneYearAgo)));
                }
            }
        }

        JPAQuery<OrderStatisticsDTO> query = new JPAQuery<>(entityManager)
                .select(
                        Projections.constructor(
                                OrderStatisticsDTO.class,
                                dateTemplate(Date.class, "CAST({0} AS DATE)", qOrder.completedAt).as("date"),
                                qMeal.price.sum().as("sale")
                        )
                )
                .from(qOrder)
                .join(qOrder.meals, qMeal)
                .where(conditions)
                .groupBy(dateTemplate(String.class, "CAST({0} AS DATE)", qOrder.completedAt))
                .orderBy(dateTemplate(String.class, "CAST({0} AS DATE)", qOrder.completedAt).asc());

        return query.fetch();
    }

    public List<MealStatisticsDTO> getMealStatistics(OrderStatisticCriteria criteria) {
        QOrder qOrder = QOrder.order;
        QMeal qMeal = QMeal.meal;
        UUID cateringFirmId = springContextRetriever.getCurrentUserIdFromJwt();
    
        BooleanBuilder conditions = new BooleanBuilder();
        conditions.and(qMeal.cateringFirmData.cateringFirmId.eq(cateringFirmId))
                .and(qOrder.orderStatus.eq(OrderStatus.COMPLETED));
    
        if(criteria.getStatisticsPeriod() == null) {
            if (criteria.getStartDate() != null) {
                conditions.and(qOrder.completedAt.goe(criteria.getStartDate()));
            }
            if (criteria.getEndDate() != null) {
                conditions.and(qOrder.completedAt.loe(criteria.getEndDate()));
            }
        }
        else {
            switch(criteria.getStatisticsPeriod()) {
                case WEEK -> {
                    Instant oneWeekAgo = LocalDateTime.now().minusWeeks(1)
                            .atZone(ZoneId.systemDefault())
                            .toInstant();
                    conditions.and(qOrder.completedAt.goe(Timestamp.from(oneWeekAgo)));
                }
                case MONTH -> {
                    Instant oneMonthAgo = LocalDateTime.now().minusMonths(1)
                            .atZone(ZoneId.systemDefault())
                            .toInstant();
                    conditions.and(qOrder.completedAt.goe(Timestamp.from(oneMonthAgo)));
                }
                case YEAR -> {
                    Instant oneYearAgo = LocalDateTime.now().minusYears(1)
                            .atZone(ZoneId.systemDefault())
                            .toInstant();
                    conditions.and(qOrder.completedAt.goe(Timestamp.from(oneYearAgo)));
                }
            }
        }
    
        JPAQuery<MealStatisticsDTO> query = new JPAQuery<>(entityManager)
                .select(
                        Projections.constructor(
                                MealStatisticsDTO.class,
                                Projections.constructor(
                                        MealGetDTO.class,
                                        qMeal.mealId,
                                        qMeal.name,
                                        qMeal.price,
                                        qMeal.description,
                                        qMeal.photo,
                                        qMeal.ingredients,
                                        qMeal.cateringFirmData.cateringFirmId,
                                        qMeal.cateringFirmData.name
                                ),
                                qMeal.orders.size().castToNum(Long.class).as("quantitySold"),
                                qMeal.price.multiply(qMeal.orders.size()).sum().as("totalSalesValue")
                        )
                )
                .from(qMeal)
                .join(qMeal.orders, qOrder)
                .where(conditions)
                .groupBy(qMeal.mealId, qMeal.name, qMeal.price, qMeal.description, qMeal.photo, qMeal.cateringFirmData.cateringFirmId, qMeal.cateringFirmData.name)
                .orderBy(qMeal.orders.size().desc(), qMeal.price.multiply(qMeal.orders.size()).desc());
    
        return query.fetch();
    }
    
}
