package com.ifinrelax.repository.currency;

import com.ifinrelax.entity.currency.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Timur Berezhnoi
 */
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}
