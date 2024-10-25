package org.ton.tonapi.sync.methods;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ton.exception.TONAPIError;
import org.ton.schema.rates.ChartRates;
import org.ton.schema.rates.MarketsTonRates;
import org.ton.schema.rates.Rates;
import org.ton.tonapi.sync.TonapiClientBase;

public class RatesMethod extends TonapiClientBase {

  public RatesMethod(TonapiClientBase client) {
    super(client);
  }

  /**
   * Get the token price to the currency.
   *
   * @param tokens     List of tokens. Accepts TON and jetton master addresses. Example: ["TON",
   *                   "UQB8ANV_ynITQr1qHXADHDKYUAQ9VFcCRDZB7h4aPuPKuFtm"]
   * @param currencies List of currencies. Accepts TON and all possible fiat currencies. Example:
   *                   ["TON","USD", "RUB"]
   * @return Rates object containing the rates information
   * @throws TONAPIError if the request fails
   */
  public Rates getPrices(List<String> tokens, List<String> currencies) throws TONAPIError {
    String method = "v2/rates";
    Map<String, Object> params = new HashMap<>();
    params.put("tokens", String.join(",", tokens));
    params.put("currencies", String.join(",", currencies));
    return this.get(method, params, null, new TypeReference<Rates>() {
    });
  }

  /**
   * Get the token price to the currency.
   *
   * @param token       Accepts jetton master address
   * @param currency    Accepts fiat currency, example: "USD", "RUB" and so on
   * @param startDate   Optional start date
   * @param endDate     Optional end date
   * @param pointsCount Optional points count
   * @return ChartRates object containing the chart rates information
   * @throws TONAPIError if the request fails
   */
  public ChartRates getChart(String token, String currency, Long startDate, Long endDate,
      Integer pointsCount) throws TONAPIError {
    String method = "v2/rates/chart";
    Map<String, Object> params = new HashMap<>();
    params.put("token", token);
    params.put("currency", currency != null ? currency : "usd");
    params.put("points_count", pointsCount != null ? pointsCount : 200);
    if (startDate != null) {
      params.put("start_date", startDate);
    }
    if (endDate != null) {
      params.put("end_date", endDate);
    }
    return this.get(method, params, null, new TypeReference<ChartRates>() {
    });
  }

  /**
   * Get the TON price from markets.
   *
   * @return MarketsTonRates object containing the TON price from markets
   * @throws TONAPIError if the request fails
   */
  public MarketsTonRates getTonPriceFromMarkets() throws TONAPIError {
    String method = "v2/rates/markets";
    return this.get(method, null, null, new TypeReference<MarketsTonRates>() {
    });
  }
}
