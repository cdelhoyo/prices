package com.iopl.prices.inbound.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponse(
        Long productId,
        BrandResponse brand,
        Long priceList,
        LocalDateTime applicationDate,
        BigDecimal price

) { }
