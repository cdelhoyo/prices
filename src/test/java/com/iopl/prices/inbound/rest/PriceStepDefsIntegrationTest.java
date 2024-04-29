package com.iopl.prices.inbound.rest;

import com.iopl.prices.inbound.rest.dto.PriceResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@CucumberContextConfiguration
public class PriceStepDefsIntegrationTest extends CucumberSpringContextConfig {

    private String date;
    private HttpStatusCode status;
    private ResponseEntity<PriceResponse> response;

    @Given("date {}")
    public void store_date(String date) throws Throwable {
        this.date = date;
    }

    @When("the client calls api price")
    public void the_client_call() throws Throwable {
        long brandId = 1L;
        long productId = 35455L;
        String url = STR."http://localhost:\{port}/api/prices?applicationDate=\{date}&productId=\{productId}&brandId=\{brandId}";
        try {
            response = restTemplate.getForEntity(url, PriceResponse.class);
            status = response.getStatusCode();
        } catch (HttpClientErrorException exception) {
            status = exception.getStatusCode();
        }
    }

    @Then("the client receives status code of {int}")
    public void the_client_receives_status_code_of(int expectedStatusCode) throws Throwable {
        assertEquals(expectedStatusCode, status.value());
    }

    @And("the client receives price {bigdecimal} and brand {string}")
    public void the_client_receives_server_version_body(BigDecimal expectedPrice, String expectedBrand) throws Throwable {
        assertEquals(expectedPrice, Objects.requireNonNull(response.getBody()).price());
        assertEquals(expectedBrand, Objects.requireNonNull(response.getBody()).brand().name());
    }
}