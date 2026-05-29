package pma.store.exchange.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import pma.store.exchange.dto.AwesomeApiQuoteDTO;
import pma.store.exchange.exception.CurrencyPairNotFoundException;
import pma.store.exchange.exception.ExternalApiException;

import java.util.Map;

/**
 * Integra com a AwesomeAPI (https://economia.awesomeapi.com.br).
 *
 * Endpoint usado: GET /json/last/{FROM}-{TO}
 * Exemplo: GET https://economia.awesomeapi.com.br/json/last/USD-BRL
 *
 * Resposta:
 * {
 *   "USDBRL": { "bid": "4.97", "ask": "4.99", "create_date": "2021-09-01 14:23:42" }
 * }
 */
@Component
public class AwesomeApiClient {

    private final RestClient restClient;

    public AwesomeApiClient(
            @Value("${exchange.api.base-url}") String baseUrl,
            RestClient.Builder builder) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    @SuppressWarnings("unchecked")
    public AwesomeApiQuoteDTO fetchQuote(String from, String to) {
        String pair = from.toUpperCase() + "-" + to.toUpperCase();
        String key  = from.toUpperCase() + to.toUpperCase();

        try {
            Map<String, Object> body = restClient.get()
                    .uri("/{pair}", pair)
                    .retrieve()
                    .body(Map.class);

            if (body == null || !body.containsKey(key)) {
                throw new CurrencyPairNotFoundException(from, to);
            }

            Map<String, String> quote = (Map<String, String>) body.get(key);

            AwesomeApiQuoteDTO dto = new AwesomeApiQuoteDTO();
            dto.setBuy(quote.get("bid"));
            dto.setSell(quote.get("ask"));
            dto.setDate(quote.get("create_date"));
            return dto;

        } catch (HttpClientErrorException.NotFound e) {
            throw new CurrencyPairNotFoundException(from, to);
        } catch (CurrencyPairNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ExternalApiException(
                    "Failed to fetch exchange rate from external API: " + e.getMessage(), e);
        }
    }
}