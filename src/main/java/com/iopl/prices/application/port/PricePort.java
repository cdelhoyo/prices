package com.iopl.prices.application.port;

import com.iopl.prices.application.domain.Price;
import com.iopl.prices.application.domain.PriceAsk;

public interface PricePort {
    Price getPrice(PriceAsk ask);
}
