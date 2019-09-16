package com.example.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FarePricesDto {
    private FarePriceDto business;
    private FarePriceDto economy;
    private FarePriceDto first;
}