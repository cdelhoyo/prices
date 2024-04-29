package com.iopl.prices.outbound.database;

import com.iopl.prices.outbound.database.entity.PriceEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PriceRepository extends ListCrudRepository<PriceEntity, String> {

    Optional<PriceEntity> findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc
            (Long brandId, Long productId, LocalDateTime startDate, LocalDateTime endDate);
}
