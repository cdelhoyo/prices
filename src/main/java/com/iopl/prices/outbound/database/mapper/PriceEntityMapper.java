package com.iopl.prices.outbound.database.mapper;

import com.iopl.prices.application.domain.Price;
import com.iopl.prices.outbound.database.entity.PriceEntity;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {
    Price priceToDomain(PriceEntity entity, LocalDateTime applicationDate);
}
