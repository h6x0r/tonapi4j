package org.ton.schema.rates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRates {
    private Map<String, String> prices;
    private Map<String, String> diff24h;
    private Map<String, String> diff7d;
    private Map<String, String> diff30d;
}
