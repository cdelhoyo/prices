package com.iopl.prices.application.usecase;

import com.iopl.prices.application.port.PricePort;
import com.iopl.prices.application.domain.Price;
import com.iopl.prices.application.domain.PriceAsk;
import org.springframework.stereotype.Component;

@Component
public class PriceQuery {

    private final PricePort pricePort;

    public PriceQuery(PricePort pricePort) {
        this.pricePort = pricePort;
    }

    public Price run(PriceAsk ask) {
        return pricePort.getPrice(ask);
    }
}
