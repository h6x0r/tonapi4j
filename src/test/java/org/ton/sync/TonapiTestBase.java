package org.ton.sync;

import org.junit.jupiter.api.BeforeEach;
import org.ton.tonapi.sync.Tonapi;

public class TonapiTestBase {

    private static final String API_KEY = "AGAGTULGU7DKUXAAAAAGOFZNLVY2GWKD5JH2G3N3OR2NDGBPI5FEPHO3D3FP3LXIG3AYCOI";
    protected Tonapi tonapi;
    protected Tonapi testnetTonapi;

    @BeforeEach
    public void setUp() {
        tonapi = new Tonapi(API_KEY, false, 10);
        testnetTonapi = new Tonapi(API_KEY, true, 10);
    }
}
