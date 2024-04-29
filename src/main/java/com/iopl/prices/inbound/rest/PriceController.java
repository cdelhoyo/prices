package com.iopl.prices.inbound.rest;


import com.iopl.prices.application.usecase.PriceQuery;
import com.iopl.prices.application.domain.Price;
import com.iopl.prices.application.domain.PriceAsk;
import com.iopl.prices.inbound.rest.dto.PriceRequest;
import com.iopl.prices.inbound.rest.dto.PriceResponse;
import com.iopl.prices.inbound.rest.mapper.PriceMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/prices")
public class PriceController {

    private final PriceMapper mapper;
    private final PriceQuery priceQuery;

    public PriceController(
        PriceMapper mapper,
        PriceQuery priceQuery
    ) {
        this.mapper = mapper;
        this.priceQuery = priceQuery;
    }

    @Operation(summary = "Get price by application date, product and brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the price",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PriceResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })
    @GetMapping
    public ResponseEntity<PriceResponse> getPrice(@Valid PriceRequest request) {
        PriceAsk ask = mapper.askToDomain(request);
        Price price = priceQuery.run(ask);
        return ResponseEntity.ok(mapper.priceToResponse(price));
    }
}
