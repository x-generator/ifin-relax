package com.ifinrelax.controller.currency;

import com.ifinrelax.entity.currency.Currency;
import com.ifinrelax.service.currency.CurrencyService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static com.ifinrelax.constant.Route.CURRENCIES;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Timur Berezhnoi
 */
public class CurrencyControllerTest {

    private final CurrencyService currencyService = mock(CurrencyService.class);
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(new CurrencyController(currencyService)).build();
    }

    @Test
    public void shouldGetListOfCurrencies() throws Exception {
        // Given
        when(currencyService.getAllCurrencies()).thenReturn(singletonList(new Currency("$", "USD")));

        // When - Then
        mockMvc.perform(get(CURRENCIES))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].sign", is("$")))
                .andExpect(jsonPath("$[0].code", is("USD")));
    }
}
