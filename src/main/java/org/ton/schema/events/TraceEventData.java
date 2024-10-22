package org.ton.schema.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.Address;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraceEventData {
    private List<Address> accounts;
    private String hash;
}
