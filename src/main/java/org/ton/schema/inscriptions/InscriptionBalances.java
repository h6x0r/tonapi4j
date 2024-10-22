package org.ton.schema.inscriptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InscriptionBalances {
    private List<InscriptionBalance> inscriptions;
}
