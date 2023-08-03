package pingwit.beautysaloon.integration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping
    public ExchangeRateDTO getTwoExchangeRates(){
return service.findTwoRates();
    }
    @GetMapping("/ccy")
    public ExchangeRateDTO getExchangeRates(@PathVariable String ccy){
return service.findRateByValuta(ccy);
    }
}
