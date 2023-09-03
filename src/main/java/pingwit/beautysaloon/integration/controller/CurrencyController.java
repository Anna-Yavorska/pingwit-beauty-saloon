package pingwit.beautysaloon.integration.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pingwit.beautysaloon.integration.controller.dto.CurrencyDTO;
import pingwit.beautysaloon.integration.service.CurrencyService;

import java.util.List;

@Tag(name = "Exchange rate management API", description = "API for Read operation with exchange rate")
@RestController
@RequestMapping("/currency")
public class CurrencyController {
    private final CurrencyService service;

    public CurrencyController(CurrencyService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get exchange rate",
            description = "Get information about exchange rate. If currency code will be invalid, empty array will be returned.")
    public List<CurrencyDTO> getExchangeRates(@RequestParam(name = "currencyCode", required = false) String currencyCode) {
        return service.findRate(currencyCode);
    }
}
