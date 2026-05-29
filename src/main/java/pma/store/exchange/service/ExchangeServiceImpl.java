package pma.store.exchange.service;

import org.springframework.stereotype.Service;
import pma.store.exchange.client.AwesomeApiClient;
import pma.store.exchange.dto.AwesomeApiQuoteDTO;
import pma.store.exchange.dto.ExchangeResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final AwesomeApiClient awesomeApiClient;

    public ExchangeServiceImpl(AwesomeApiClient awesomeApiClient) {
        this.awesomeApiClient = awesomeApiClient;
    }

    @Override
    public ExchangeResponse getExchangeRate(String from, String to) {
        validateCurrencyCode(from, "from");
        validateCurrencyCode(to, "to");

        AwesomeApiQuoteDTO quote = awesomeApiClient.fetchQuote(from, to);

        BigDecimal sell = new BigDecimal(quote.getSell()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal buy  = new BigDecimal(quote.getBuy()).setScale(2, RoundingMode.HALF_UP);

        return new ExchangeResponse(sell, buy, quote.getDate());
    }

    private void validateCurrencyCode(String code, String field) {
        if (code == null || !code.matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException(
                    "Invalid currency code for '" + field + "': '" + code
                    + "'. Must be a 3-letter ISO 4217 code (e.g. USD, BRL, EUR).");
        }
    }
}