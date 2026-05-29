package pma.store.exchange.exception;

public class CurrencyPairNotFoundException extends RuntimeException {

    public CurrencyPairNotFoundException(String from, String to) {
        super("Exchange rate not found for currency pair: " + from + " -> " + to
                + ". Check if both currency codes are valid (e.g. USD, BRL, EUR).");
    }
}