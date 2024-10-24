package org.ton.sync.methods;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ton.schema.accounts.Accounts;
import org.ton.schema.message.MessageConsequences;
import org.ton.schema.wallet.AddressProof;
import org.ton.schema.wallet.BocParams;
import org.ton.schema.wallet.Domain;
import org.ton.schema.wallet.Proof;
import org.ton.sync.TonapiTestBase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.ACCEPT_LANGUAGE_EN;
import static org.ton.utils.Constants.ACCOUNT_ID;

public class TestWalletMethod extends TonapiTestBase {

    private static final String PUBLIC_KEY = "your_public_key_here"; // Replace with a valid public key
    private static final String XTONCONNECT_AUTH = "your_xTonconnectAuth_token_here"; // Replace with a valid token
    private static final String BOC = "te6cckECGAEAAxEABAgAAUyXARUXFgEU/wD0pBP0vPLICwICASADEAIBSAQHAubQAdDTAyFxsJJfBOAi10nBIJJfBOAC0x8hghBwbHVnvSKCEGRzdHK9sJJfBeAD+kAwIPpEAcjKB8v/ydDtRNCBAUDXIfQEMFyBAQj0Cm+hMbOSXwfgBdM/yCWCEHBsdWe6kjgw4w0DghBkc3RyupJfBuMNBQYAeAH6APQEMPgnbyIwUAqhIb7y4FCCEHBsdWeDHrFwgBhQBMsFJs8WWPoCGfQAy2kXyx9SYMs/IMmAQPsABgCKUASBAQj0WTDtRNCBAUDXIMgBzxb0AMntVAFysI4jghBkc3Rygx6xcIAYUAXLBVADzxYj+gITy2rLH8s/yYBA+wCSXwPiAgEgCA8CASAJDgIBWAoLAD2ynftRNCBAUDXIfQEMALIygfL/8nQAYEBCPQKb6ExgAgEgDA0AGa3OdqJoQCBrkOuF/8AAGa8d9qJoQBBrkOuFj8AAEbjJftRNDXCx+ABZvSQrb2omhAgKBrkPoCGEcNQICEekk30pkQzmkD6f+YN4EoAbeBAUiYcVnzGEBPjygwjXGCDTH9Mf0x8C+CO78mTtRNDTH9Mf0//0BNFRQ7ryoVFRuvKiBfkBVBBk+RDyo/gAJKTIyx9SQMsfUjDL/1IQ9ADJ7VT4DwHTByHAAJ9sUZMg10qW0wfUAvsA6DDgIcAB4wAhwALjAAHAA5Ew4w0DpMjLHxLLH8v/ERITFABu0gf6ANTUIvkABcjKBxXL/8nQd3SAGMjLBcsCIs8WUAX6AhTLaxLMzMlz+wDIQBSBAQj0UfKnAgBwgQEI1xj6ANM/yFQgR4EBCPRR8qeCEG5vdGVwdIAYyMsFywJQBs8WUAT6AhTLahLLH8s/yXP7AAIAbIEBCNcY+gDTPzBSJIEBCPRZ8qeCEGRzdHJwdIAYyMsFywJQBc8WUAP6AhPLassfEss/yXP7AAAK9ADJ7VQAUQAAAAAAAAAqgqCyVD0G/sCqyVLp7HOL5WqxtgJ/wMGqgXrhS00e0vtAAQAXAAYAAAB5S1or";

    @Test
    @Disabled
    public void testGetBackupInfo() throws Exception {
        String response = tonapi.getWallet().getBackupInfo(XTONCONNECT_AUTH);
        assertNotNull(response);
    }

    @Test
    @Disabled
    public void testAccountVerification() throws Exception {
        AddressProof addressProof = AddressProof.builder()
                .address("0:97146a46acc2654y27947f14c4a4b14273e954f78bc017790b41208b0043200b")
                .proof(Proof.builder()
                        .timestamp(1678275313L)
                        .signature("string")
                        .payload("84jHVNLQmZsAAAAAZB0Zryi2wqVJI-KaKNXOvCijEi46YyYzkaSHyJrMPBMOkVZa")
                        .stateInit("string")
                        .domain(Domain.builder()
                                .lengthBytes(1073741824L)
                                .value("string")
                                .build())
                        .build())
                .build();

        String token = tonapi.getWallet().accountVerification(addressProof);
        assertNotNull(token);
    }

    @Test
    @Disabled
    public void testGetByPublicKey() throws Exception {
        Accounts accounts = tonapi.getWallet().getByPublicKey(PUBLIC_KEY);
        assertNotNull(accounts);
        assertNotNull(accounts.getAccounts());
    }

    @Test
    public void testGetAccountSeqno() throws Exception {
        Integer seqno = tonapi.getWallet().getAccountSeqno(ACCOUNT_ID);
        assertNotNull(seqno);
    }

    @Test
    @Disabled
    public void testEmulate() throws Exception {
        BocParams bocParams = BocParams.builder()
                .boc(BOC)
                .build();

        MessageConsequences consequences = tonapi.getWallet().emulate(bocParams, ACCEPT_LANGUAGE_EN);
        assertNotNull(consequences);
    }
}
