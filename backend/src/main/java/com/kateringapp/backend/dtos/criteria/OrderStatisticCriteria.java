package com.kateringapp.backend.dtos.criteria;

import com.kateringapp.backend.entities.StatisticsPeriod;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class OrderStatisticCriteria {
    Timestamp startDate;
    Timestamp endDate;
    StatisticsPeriod statisticsPeriod;
}
