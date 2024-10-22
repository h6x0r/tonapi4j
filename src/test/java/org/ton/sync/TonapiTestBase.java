package org.ton.sync;

import org.junit.jupiter.api.BeforeEach;
import org.ton.tonapi.sync.Tonapi;

public class TonapiTestBase {

    private static final String API_KEY = "AGAGTULGBG6ODXAAAAAAJESYBZKG4TE23XN3665FH2ZLQXISGSOSGUC3PHQQV6NRNB6TNQI";
    protected Tonapi tonapi;
    protected Tonapi testnetTonapi;

    @BeforeEach
    public void setUp() {
        tonapi = new Tonapi(API_KEY, false, 10);
        testnetTonapi = new Tonapi(API_KEY, true, 10);
    }
}
