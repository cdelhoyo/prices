package com.iopl.prices.inbound.rest.mapper;

import com.iopl.prices.application.domain.Price;
import com.iopl.prices.application.domain.PriceAsk;
import com.iopl.prices.inbound.rest.dto.PriceRequest;
import com.iopl.prices.inbound.rest.dto.PriceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    PriceAsk askToDomain(PriceRequest request);

    PriceResponse priceToResponse(Price price);
}
