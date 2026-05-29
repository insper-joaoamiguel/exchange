package pma.store.exchange.service;

import pma.store.exchange.dto.ExchangeResponse;

public interface ExchangeService {

    ExchangeResponse getExchangeRate(String from, String to);
}