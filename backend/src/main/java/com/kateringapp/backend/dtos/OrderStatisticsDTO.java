package com.kateringapp.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
public class OrderStatisticsDTO {
    Date date;
    BigDecimal sale;
}
