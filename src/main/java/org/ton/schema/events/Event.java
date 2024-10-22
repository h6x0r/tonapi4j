package org.ton.schema.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.events.action.Action;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private String eventId;
    private Long timestamp;
    private List<Action> actions;
    private List<ValueFlow> valueFlow;
    private Boolean isScam;
    private Long lt;
    private Boolean inProgress;
}
