package com.iopl.prices.outbound;

import com.iopl.prices.application.domain.PriceAsk;
import com.iopl.prices.application.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PriceAdapterIT {

    @Autowired
    private PriceAdapter priceAdapter;

    private final Long PRODUCT_ID = 35455L;
    private final Long BRAND_ID = 1L;

    private static Stream<Arguments> provideDateAndPrice() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 14, 10, 0), BigDecimal.valueOf(35.50), "ZARA"),
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 14, 16, 0), BigDecimal.valueOf(25.45), "ZARA"),
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 14, 21, 0), BigDecimal.valueOf(35.50), "ZARA"),
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 15, 10, 0), BigDecimal.valueOf(30.50), "ZARA"),
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 15, 21, 0), BigDecimal.valueOf(38.95), "ZARA")
        );
    }

    @ParameterizedTest
    @MethodSource("provideDateAndPrice")
    void givenAnAskWithDate_whenGetPrice_thenReturnPrice(LocalDateTime date, BigDecimal expectedPrice, String expectedBrand) {
        var result = priceAdapter.getPrice(new PriceAsk(date, PRODUCT_ID, BRAND_ID));

        assertEquals(expectedPrice, result.price());
        assertEquals(expectedBrand, result.brand().name());
    }

    @Test
    void givenAnAskWithDayIn2010_whenGetPrice_thenReturnNotFoundException() {
        var june15At21 = LocalDateTime.of(2010, Month.JUNE, 15, 21, 0);

        assertThrows(NotFoundException.class, () -> priceAdapter.getPrice(new PriceAsk(june15At21, PRODUCT_ID, BRAND_ID)));
    }
}