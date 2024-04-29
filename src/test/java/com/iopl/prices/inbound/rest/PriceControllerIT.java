package com.iopl.prices.inbound.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PriceControllerIT {

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;

    @BeforeEach
    public void setup() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    private static Stream<Arguments> provideDateAndPrice() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 14, 10, 0), BigDecimal.valueOf(35.50), "ZARA"),
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 14, 16, 0), BigDecimal.valueOf(25.45), "ZARA"),
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 14, 21, 0), BigDecimal.valueOf(35.50), "ZARA"),
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 15, 10, 0), BigDecimal.valueOf(30.50), "ZARA"),
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 15, 21, 0), BigDecimal.valueOf(38.95), "ZARA")
        );
    }

    private static Stream<Arguments> requestWithNullFields() {
        return Stream.of(
                Arguments.of(null, PRODUCT_ID, BRAND_ID),
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 14, 16, 0), null, BRAND_ID),
                Arguments.of(LocalDateTime.of(2020, Month.JUNE, 14, 21, 0), PRODUCT_ID, null)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDateAndPrice")
    void givenARequestWithDate_whenGetPrice_thenReturnOkAndPrice(LocalDateTime date, BigDecimal expectedPrice, String expectedBrand) throws Exception {
        String url = STR."/api/prices?applicationDate=\{date}&productId=\{PRODUCT_ID}&brandId=\{BRAND_ID}";

        mvc.perform(get(url)).andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andExpect(jsonPath("$.brand.name").value(expectedBrand));
    }

    @Test
    void givenARequestWithDayIn2010_whenGetPrice_thenReturnNotFound() throws Exception {
        var dayIn2010 = LocalDateTime.of(2010, Month.JUNE, 15, 21, 0);
        String url = STR."/api/prices?applicationDate=\{dayIn2010}&productId=\{PRODUCT_ID}&brandId=\{BRAND_ID}";

        mvc.perform(get(url)).andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @MethodSource("requestWithNullFields")
    void givenARequestWithNullFields_whenGetPrice_thenReturnBadRequest(LocalDateTime date, Long productId, Long brandId) throws Exception {
        String url = STR."/api/prices?applicationDate=\{date}&productId=\{productId}&brandId=\{brandId}";

        mvc.perform(get(url)).andExpect(status().isBadRequest());
    }

}