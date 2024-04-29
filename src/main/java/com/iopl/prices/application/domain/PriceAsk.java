package com.iopl.prices.application.domain;

import java.time.LocalDateTime;

public record PriceAsk(
        LocalDateTime applicationDate,
        Long productId,
        Long brandId
) {
}
