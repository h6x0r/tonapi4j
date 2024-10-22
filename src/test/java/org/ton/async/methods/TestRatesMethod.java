package org.ton.async.methods;

import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;
import org.ton.schema.rates.ChartRates;
import org.ton.schema.rates.MarketsTonRates;
import org.ton.schema.rates.Rates;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.END_DATE;
import static org.ton.utils.Constants.JETTON_ID;
import static org.ton.utils.Constants.START_DATE;

public class TestRatesMethod extends AsyncTonapiTestBase {

    private static final List<String> TOKENS = Arrays.asList("TON");
    private static final List<String> CURRENCIES = Arrays.asList("USD", "RUB");

    @Test
    public void testGetRates() throws Exception {
        CompletableFuture<Rates> future = tonapi.getRates().getPrices(TOKENS, CURRENCIES);
        Rates response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetChart() throws Exception {
        CompletableFuture<ChartRates> future = tonapi.getRates().getChart(JETTON_ID, CURRENCIES.get(0), START_DATE, END_DATE, null);
        ChartRates response = future.get();
        assertNotNull(response);
    }

    @Test
    public void testGetTonPriceFromMarkets() throws Exception {
        CompletableFuture<MarketsTonRates> future = tonapi.getRates().getTonPriceFromMarkets();
        MarketsTonRates response = future.get();
        assertNotNull(response);
    }
}
