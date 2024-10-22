package org.ton.schema.events.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Action {
    private String type;
    private String status;

    @JsonProperty("TonTransfer")
    private TonTransferAction tonTransfer;

    @JsonProperty("ContractDeploy")
    private ContractDeployAction contractDeploy;

    @JsonProperty("JettonTransfer")
    private JettonTransferAction jettonTransfer;

    @JsonProperty("JettonBurn")
    private JettonBurnAction jettonBurn;

    @JsonProperty("JettonMint")
    private JettonMintAction jettonMint;

    @JsonProperty("NftItemTransfer")
    private NftItemTransferAction nftItemTransfer;

    @JsonProperty("Subscribe")
    private SubscriptionAction subscribe;

    @JsonProperty("UnSubscribe")
    private UnSubscriptionAction unSubscribe;

    @JsonProperty("AuctionBid")
    private AuctionBidAction auctionBid;

    @JsonProperty("NftPurchase")
    private NftPurchaseAction nftPurchase;

    @JsonProperty("DepositStake")
    private DepositStakeAction depositStake;

    @JsonProperty("WithdrawStake")
    private WithdrawStakeAction withdrawStake;

    @JsonProperty("WithdrawStakeRequest")
    private WithdrawStakeRequestAction withdrawStakeRequest;

    @JsonProperty("ElectionsDepositStake")
    private ElectionsDepositStakeAction electionsDepositStake;

    @JsonProperty("ElectionsRecoverStake")
    private ElectionsRecoverStakeAction electionsRecoverStake;

    @JsonProperty("JettonSwap")
    private JettonSwapAction jettonSwap;

    @JsonProperty("SmartContractExec")
    private SmartContractAction smartContractExec;

    @JsonProperty("DomainRenew")
    private DomainRenewAction domainRenew;

    @JsonProperty("InscriptionTransfer")
    private InscriptionTransferAction inscriptionTransfer;

    @JsonProperty("InscriptionMint")
    private InscriptionMintAction inscriptionMint;

    @JsonProperty("base_transactions")
    private List<String> baseTransactions;

    @JsonProperty("simple_preview")
    private ActionSimplePreview simplePreview;
}
