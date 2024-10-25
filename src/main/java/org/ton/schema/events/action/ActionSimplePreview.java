package org.ton.schema.events.action;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.accounts.AccountAddress;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionSimplePreview {

  private String name;
  private String description;
  private String actionImage;
  private String value;
  private String valueImage;
  private List<AccountAddress> accounts;
}
