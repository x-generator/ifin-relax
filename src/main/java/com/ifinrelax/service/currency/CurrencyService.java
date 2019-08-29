package com.ifinrelax.service.currency;

import com.ifinrelax.entity.currency.Currency;
import com.ifinrelax.repository.currency.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Timur Berezhnoi
 */
@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public Currency findCurrencyById(Integer id) {
        return currencyRepository.findOne(id);
    }
}
