package com.ifinrelax.controller.currency;

import com.ifinrelax.entity.currency.Currency;
import com.ifinrelax.service.currency.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ifinrelax.constant.Route.CURRENCIES;

/**
 * @author Timur Berezhnoi
 */
@RestController
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(CURRENCIES)
    public List<Currency> getCurrencies() {
        return currencyService.getAllCurrencies();
    }
}
