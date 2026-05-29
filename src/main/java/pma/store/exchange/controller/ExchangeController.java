package pma.store.exchange.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pma.store.exchange.dto.ExchangeResponse;
import pma.store.exchange.service.ExchangeService;

@RestController
@RequestMapping("/exchanges")
@Tag(name = "Exchange", description = "Currency exchange rate operations")
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/{from}/{to}")
    @Operation(
        summary = "Get exchange rate between two currencies",
        description = "Returns the current buy and sell exchange rate from one currency to another. E.g. GET /exchanges/USD/BRL",
        parameters = {
            @Parameter(name = "from", description = "Source currency ISO 4217 code (e.g. USD)", example = "USD", required = true),
            @Parameter(name = "to",   description = "Target currency ISO 4217 code (e.g. BRL)", example = "BRL", required = true)
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "Exchange rate found",
                content = @Content(schema = @Schema(implementation = ExchangeResponse.class),
                    examples = @ExampleObject(value = """
                        {
                          "sell": 0.82,
                          "buy": 0.80,
                          "date": "2021-09-01 14:23:42",
                          "id-account": "0195ae95-5be7-7dd3-b35d-7a7d87c404fb"
                        }
                    """))),
            @ApiResponse(responseCode = "400", description = "Invalid currency code format"),
            @ApiResponse(responseCode = "404", description = "Currency pair not found"),
            @ApiResponse(responseCode = "502", description = "External API unavailable")
        }
    )
    public ResponseEntity<ExchangeResponse> getExchangeRate(
            @PathVariable String from,
            @PathVariable String to) {

        ExchangeResponse response = exchangeService.getExchangeRate(from, to);
        return ResponseEntity.ok(response);
    }
}