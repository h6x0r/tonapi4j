package org.ton.schema.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.accounts.AccountAddress;
import org.ton.schema.events.action.Action;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEvent {
    private String description;
    private AccountAddress account;
    private Long timestamp;
    private List<Action> actions;
    private Boolean isScam;
    private Long lt;
    private Boolean inProgress;
    private Long extra;
    private String eventId;
}
