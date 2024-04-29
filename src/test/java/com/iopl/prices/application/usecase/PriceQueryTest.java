package com.iopl.prices.application.usecase;

import com.iopl.prices.application.domain.Brand;
import com.iopl.prices.application.port.PricePort;
import com.iopl.prices.application.domain.Price;
import com.iopl.prices.application.domain.PriceAsk;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceQueryTest {

    private final PricePort pricePort = mock();
    private final PriceQuery query = new PriceQuery(pricePort);

    @Test
    void givenAnAsk_whenGetPriceQuery_thenCallPricePort() {
        var applicationDate = LocalDateTime.now();
        var brandId = 3L;
        var productId = 1L;
        var ask = new PriceAsk(applicationDate, productId, brandId);
        Brand brand = new Brand(1L, "ZARA");
        Price price = new Price(brand, LocalDateTime.now(), 3L, productId, BigDecimal.valueOf(150.0), Currency.getInstance("EUR"));
        when(pricePort.getPrice(ask)).thenReturn(price);

        var result = query.run(ask);

        assertEquals(price, result);
    }

}