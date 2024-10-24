package org.ton.sync.methods;

import org.junit.jupiter.api.Test;
import org.ton.schema.rates.ChartRates;
import org.ton.schema.rates.MarketsTonRates;
import org.ton.schema.rates.Rates;
import org.ton.sync.TonapiTestBase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.END_DATE;
import static org.ton.utils.Constants.JETTON_ID;
import static org.ton.utils.Constants.START_DATE;

public class TestRatesMethod extends TonapiTestBase {

    private static final List<String> TOKENS = Collections.singletonList("TON");
    private static final List<String> CURRENCIES = Arrays.asList("USD", "RUB");

    @Test
    public void testGetRates() {
        Rates response = tonapi.getRates().getPrices(TOKENS, CURRENCIES);
        assertNotNull(response);
    }

    @Test
    public void testGetChart() {
        ChartRates response = tonapi.getRates().getChart(JETTON_ID, CURRENCIES.get(0), START_DATE, END_DATE, null);
        assertNotNull(response);
    }

    @Test
    public void testGetTonPriceFromMarkets() {
        MarketsTonRates response = tonapi.getRates().getTonPriceFromMarkets();
        assertNotNull(response);
    }
}
