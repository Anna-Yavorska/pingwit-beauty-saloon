package pingwit.beautysaloon.service;

import java.math.BigDecimal;

public interface PriceService {

    BigDecimal calculatePrice(Integer masterId, Integer procedureId);
}
