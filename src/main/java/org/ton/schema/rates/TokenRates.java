package org.ton.schema.rates;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
