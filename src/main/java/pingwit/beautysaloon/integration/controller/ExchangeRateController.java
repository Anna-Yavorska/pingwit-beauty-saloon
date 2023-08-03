package pingwit.beautysaloon.integration.controller;

import org.springframework.web.bind.annotation.*;
import pingwit.beautysaloon.integration.controller.dto.ExchangeRateDTO;
import pingwit.beautysaloon.integration.service.ExchangeRateService;

import java.util.List;

@RestController
@RequestMapping("/hryvnia")
public class ExchangeRateController {
    private final ExchangeRateService service;

    public ExchangeRateController(ExchangeRateService service) {
        this.service = service;
    }

   /* @GetMapping
    public ExchangeRateDTO getTwoExchangeRates() {
        return service.findTwoRates();
    }*/

    @GetMapping
    public ExchangeRateDTO getExchangeRates(@RequestParam String currencyCode) {
        return service.findRateByValuta(currencyCode);
    }
}
