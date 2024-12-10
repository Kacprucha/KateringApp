package com.kateringapp.backend.controllers;

import com.kateringapp.backend.dtos.OrderStatisticsDTO;
import com.kateringapp.backend.dtos.criteria.OrderStatisticCriteria;
import com.kateringapp.backend.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping
    public List<OrderStatisticsDTO> getStatisticsChart(@ModelAttribute OrderStatisticCriteria orderStatisticCriteria){
        return statisticsService.getOrderStatistics(orderStatisticCriteria);
    }
}
