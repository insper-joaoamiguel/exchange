package pma.store.exchange;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import pma.store.exchange.controller.ExchangeController;
import pma.store.exchange.dto.ExchangeResponse;
import pma.store.exchange.exception.CurrencyPairNotFoundException;
import pma.store.exchange.service.ExchangeService;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExchangeController.class)
class ExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeService exchangeService;

    @Test
    @DisplayName("GET /exchanges/{from}/{to} - should return 200 with exchange rate")
    void shouldReturnExchangeRate() throws Exception {
        ExchangeResponse mockResponse = new ExchangeResponse(
                new BigDecimal("0.82"),
                new BigDecimal("0.80"),
                "2021-09-01 14:23:42"
        );
        when(exchangeService.getExchangeRate("USD", "EUR")).thenReturn(mockResponse);

        mockMvc.perform(get("/exchanges/USD/EUR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sell").value(0.82))
                .andExpect(jsonPath("$.buy").value(0.80))
                .andExpect(jsonPath("$.date").value("2021-09-01 14:23:42"))
                .andExpect(jsonPath("$['id-account']").isNotEmpty());
    }

    @Test
    @DisplayName("GET /exchanges/{from}/{to} - should return 404 when pair not found")
    void shouldReturn404WhenPairNotFound() throws Exception {
        when(exchangeService.getExchangeRate("USD", "XYZ"))
                .thenThrow(new CurrencyPairNotFoundException("USD", "XYZ"));

        mockMvc.perform(get("/exchanges/USD/XYZ"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /exchanges/{from}/{to} - should return 400 when currency code is invalid")
    void shouldReturn400WhenInvalidCode() throws Exception {
        when(exchangeService.getExchangeRate("INVALID", "BRL"))
                .thenThrow(new IllegalArgumentException("Invalid currency code for 'from'"));

        mockMvc.perform(get("/exchanges/INVALID/BRL"))
                .andExpect(status().isBadRequest());
    }
}