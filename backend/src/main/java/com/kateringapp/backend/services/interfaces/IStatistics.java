package com.kateringapp.backend.services.interfaces;

import com.kateringapp.backend.dtos.OrderStatisticsDTO;
import com.kateringapp.backend.dtos.criteria.OrderStatisticCriteria;

import java.util.List;

public interface IStatistics {
    List<OrderStatisticsDTO> getOrderStatistics(OrderStatisticCriteria criteria);
}
