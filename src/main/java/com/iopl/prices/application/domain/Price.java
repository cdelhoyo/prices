package com.iopl.prices.application.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public record Price(
    Brand brand,
    LocalDateTime applicationDate,
    Long priceList,
    Long productId,
    BigDecimal price,
    Currency currency
) { }
