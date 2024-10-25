package org.ton.schema.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressForm {

  private String rawForm;
  private AddressFormB64 bounceable;
  private AddressFormB64 nonBounceable;
  private String givenType;
  private Boolean testOnly;
}
