package pingwit.beautysaloon.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pingwit.beautysaloon.service.PriceService;

import java.math.BigDecimal;
@Tag(name = "Price management API", description = "API for Read operation with price")
@RestController
@RequestMapping("/price")
public class PriceController {
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    public BigDecimal calculatePrice(@RequestParam Integer masterId, @RequestParam Integer procedureId) {
        return priceService.calculatePrice(masterId, procedureId);
    }
}
