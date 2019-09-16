package com.example.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.springframework.util.StringUtils.split;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PriceDto {
    private String currency;
    private String amount;

    public static PriceDto from(final String price) {
        PriceDto priceDto = new PriceDto();
        String[] priceSplit = split(price, " ");
        if (null != priceSplit && priceSplit.length > 1) {
            priceDto.setCurrency(priceSplit[0]);
            priceDto.setAmount(priceSplit[1]);
        }
        return priceDto;
    }
}