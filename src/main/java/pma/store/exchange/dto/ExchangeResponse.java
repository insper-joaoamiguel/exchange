package pma.store.exchange.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ExchangeResponse {

    private BigDecimal sell;
    private BigDecimal buy;
    private String date;

    @com.fasterxml.jackson.annotation.JsonProperty("id-account")
    private String idAccount;

    public ExchangeResponse() {}

    public ExchangeResponse(BigDecimal sell, BigDecimal buy, String date) {
        this.sell = sell;
        this.buy = buy;
        this.date = date;
        // Gera um id-account único por requisição (simula o campo da spec)
        this.idAccount = UUID.randomUUID().toString();
    }

    public BigDecimal getSell() { return sell; }
    public BigDecimal getBuy() { return buy; }
    public String getDate() { return date; }
    public String getIdAccount() { return idAccount; }
}