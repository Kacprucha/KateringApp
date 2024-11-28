package com.kateringapp.backend.specifications;

import com.kateringapp.backend.dtos.criteria.OrderCriteria;
import com.kateringapp.backend.entities.Meal;
import com.kateringapp.backend.entities.order.Order;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class OrderSpecification {

    public static Specification<Order> matchesCriteria(OrderCriteria criteria, UUID userId) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Existing criteria
            addPredicateIfNotNull(predicates, criteria.minRate(), minRate ->
                    cb.greaterThanOrEqualTo(root.get("rate"), minRate));

            addPredicateIfNotNull(predicates, criteria.maxRate(), maxRate ->
                    cb.lessThanOrEqualTo(root.get("rate"), maxRate));

            addPredicateIfNotNull(predicates, criteria.orderStatus(), orderStatus ->
                    cb.equal(root.get("orderStatus"), orderStatus));

            addPredicateIfNotNull(predicates, criteria.startingAddress(), startingAddress ->
                    cb.like(root.get("startingAddress"), "%" + startingAddress + "%"));

            addPredicateIfNotNull(predicates, criteria.destinationAddress(), destinationAddress ->
                    cb.like(root.get("destinationAddress"), "%" + destinationAddress + "%"));

            // New logic to match orders by clientId OR meals.cateringFirmData.cateringFirmId
            Predicate byClientId = cb.equal(root.get("clientId"), userId);

            Join<Order, Meal> mealJoin = root.join("meals");
            Predicate byCateringFirmId = cb.equal(mealJoin.join("cateringFirmData").get("cateringFirmId"), userId);

            // Combine the two with OR
            Predicate combined = cb.or(byClientId, byCateringFirmId);

            // Add combined predicate to the list
            predicates.add(combined);

            // Combine all predicates with AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static <T> void addPredicateIfNotNull(List<Predicate> predicates, T value,
                                                  Function<T, Predicate> predicateFunction) {
        if (value != null) {
            predicates.add(predicateFunction.apply(value));
        }
    }
}
