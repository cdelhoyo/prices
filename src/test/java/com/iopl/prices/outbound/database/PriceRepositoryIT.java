package com.iopl.prices.outbound.database;

import com.iopl.prices.application.domain.exceptions.NotFoundException;
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

@SpringBootTest
class PriceRepositoryIT {

    @Autowired
    private PriceRepository priceRepository;

    private static Stream<Arguments> provideDatePriorityAndPrice() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 14, 10, 0), 0L, BigDecimal.valueOf(35.50), "ZARA"),
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 14, 16, 0), 1L, BigDecimal.valueOf(25.45), "ZARA"),
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 14, 21, 0), 0L, BigDecimal.valueOf(35.50), "ZARA"),
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 15, 10, 0), 1L, BigDecimal.valueOf(30.50), "ZARA"),
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 15, 21, 0), 1L, BigDecimal.valueOf(38.95), "ZARA")
        );
    }

    @ParameterizedTest
    @MethodSource("provideDatePriorityAndPrice")
    void givenDate_whenFindFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc_thenReturnPriceWithPriority(
            LocalDateTime date, Long expectedPriority, BigDecimal expectedPrice, String expectedBrand
    ) {
        Long brandId = 1L;
        Long productId = 35455L;
        var result = priceRepository
                .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(brandId, productId, date, date)
                .orElseThrow(NotFoundException::new);

        assertEquals(expectedPriority, result.getPriority());
        assertEquals(expectedPrice, result.getPrice());
        assertEquals(expectedBrand, result.getBrand().getName());
    }
}