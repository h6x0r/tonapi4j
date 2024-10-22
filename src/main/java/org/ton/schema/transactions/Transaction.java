package org.ton.schema.transactions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ton.schema.accounts.AccountAddress;
import org.ton.schema.message.Message;
import org.ton.schema.traces.AccountStatus;
import org.ton.schema.traces.ActionPhase;
import org.ton.schema.traces.BouncePhaseType;
import org.ton.schema.traces.ComputePhase;
import org.ton.schema.traces.CreditPhase;
import org.ton.schema.traces.StoragePhase;
import org.ton.schema.traces.TransactionType;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String hash;
    private Long lt;
    private AccountAddress account;
    private Boolean success;
    private Long utime;
    private AccountStatus origStatus;
    private AccountStatus endStatus;
    private Long totalFees;
    private Long endBalance;
    private TransactionType transactionType;
    private String stateUpdateOld;
    private String stateUpdateNew;
    private Message inMsg;
    private List<Message> outMsgs;
    private String block;
    private String prevTransHash;
    private Long prevTransLt;
    private ComputePhase computePhase;
    private StoragePhase storagePhase;
    private CreditPhase creditPhase;
    private ActionPhase actionPhase;
    private BouncePhaseType bouncePhase;
    private Boolean aborted;
    private Boolean destroyed;
    private String raw;
}
