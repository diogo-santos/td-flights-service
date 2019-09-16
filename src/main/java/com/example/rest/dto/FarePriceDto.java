package com.example.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FarePriceDto {
    private PriceDto ticket;
    private PriceDto bookingFee;
    private PriceDto tax;
}
