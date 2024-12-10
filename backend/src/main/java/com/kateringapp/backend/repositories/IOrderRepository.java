package com.kateringapp.backend.repositories;

import com.kateringapp.backend.entities.Meal;
import com.kateringapp.backend.entities.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long>,
        JpaSpecificationExecutor<Order> {

    List<Order> findOrdersByMealsContaining(Meal meal);


}
