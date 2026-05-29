package pma.store.exchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Mapeia a resposta da AwesomeAPI.
 * Exemplo: GET https://economia.awesomeapi.com.br/json/last/USD-BRL
 * {
 *   "USDBRL": {
 *     "bid": "4.97",   -> preço de compra (buy)
 *     "ask": "4.99",   -> preço de venda (sell)
 *     "create_date": "2021-09-01 14:23:42"
 *   }
 * }
 */
public class AwesomeApiQuoteDTO {

    @JsonProperty("bid")
    private String buy;

    @JsonProperty("ask")
    private String sell;

    @JsonProperty("create_date")
    private String date;

    public String getBuy() { return buy; }
    public void setBuy(String buy) { this.buy = buy; }

    public String getSell() { return sell; }
    public void setSell(String sell) { this.sell = sell; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}