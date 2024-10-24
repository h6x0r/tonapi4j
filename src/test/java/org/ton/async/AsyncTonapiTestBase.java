package org.ton.async;

import org.junit.jupiter.api.BeforeEach;
import org.ton.tonapi.async.AsyncTonapi;

public class AsyncTonapiTestBase {

    private static final String API_KEY = "AGAGTULGU7DKUXAAAAAGOFZNLVY2GWKD5JH2G3N3OR2NDGBPI5FEPHO3D3FP3LXIG3AYCOI";
    protected AsyncTonapi tonapi;
    protected AsyncTonapi testnetTonapi;

    @BeforeEach
    public void setUp() {
        tonapi = new AsyncTonapi(API_KEY, false, 10);
        testnetTonapi = new AsyncTonapi(API_KEY, true, 10);
    }
}
