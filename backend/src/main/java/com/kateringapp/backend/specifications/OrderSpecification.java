package com.kateringapp.backend.specifications;

import com.kateringapp.backend.dtos.criteria.OrderCriteria;
import com.kateringapp.backend.entities.order.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class OrderSpecification {

    public static Specification<Order> matchesCriteria(OrderCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            addPredicateIfNotNull(predicates, criteria.minRate(), minRate ->
                    cb.greaterThanOrEqualTo(root.get("rate"), minRate));

            addPredicateIfNotNull(predicates, criteria.maxRate(), maxRate ->
                    cb.lessThanOrEqualTo(root.get("rate"), maxRate));

            addPredicateIfNotNull(predicates, criteria.orderStatus(), orderStatus ->
                    cb.equal(root.get("orderStatus"), orderStatus));

            addPredicateIfNotNull(predicates, criteria.clientId(), clientId ->
                    cb.equal(root.get("client").get("id"), clientId));

            addPredicateIfNotNull(predicates, criteria.startingAddress(), startingAddress ->
                    cb.like(root.get("startingAddress"), "%" + startingAddress + "%"));

            addPredicateIfNotNull(predicates, criteria.destinationAddress(), destinationAddress ->
                    cb.like(root.get("destinationAddress"), "%" + destinationAddress + "%"));

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
