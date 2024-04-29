package com.iopl.prices.outbound;

import com.iopl.prices.application.port.PricePort;
import com.iopl.prices.application.domain.Price;
import com.iopl.prices.application.domain.PriceAsk;
import com.iopl.prices.application.domain.exceptions.NotFoundException;
import com.iopl.prices.outbound.database.PriceRepository;
import com.iopl.prices.outbound.database.entity.PriceEntity;
import com.iopl.prices.outbound.database.mapper.PriceEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class PriceAdapter implements PricePort {

    private final PriceRepository priceRepository;
    private final PriceEntityMapper priceEntityMapper;

    public PriceAdapter(PriceRepository priceRepository, PriceEntityMapper priceEntityMapper) {
        this.priceRepository = priceRepository;
        this.priceEntityMapper = priceEntityMapper;
    }

    @Override
    public Price getPrice(PriceAsk ask) {
        PriceEntity priceEntity = priceRepository.findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc
                (ask.brandId(), ask.productId(), ask.applicationDate(), ask.applicationDate()).orElseThrow(NotFoundException::new);
        return priceEntityMapper.priceToDomain(priceEntity, ask.applicationDate());
    }
}
