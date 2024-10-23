package org.ton.async;

import org.junit.jupiter.api.BeforeEach;
import org.ton.tonapi.async.AsyncTonapi;

public class AsyncTonapiTestBase {

    private static final String API_KEY = "AGAGTULGTS2A7WIAAAAJWNBAJIN53OJIORIGI3DUTJVQTCMNOEZRUCSZNQ5MA3TL6WPDQ5I";
    protected AsyncTonapi tonapi;
    protected AsyncTonapi testnetTonapi;

    @BeforeEach
    public void setUp() {
        tonapi = new AsyncTonapi(API_KEY, false, 10);
        testnetTonapi = new AsyncTonapi(API_KEY, true, 10);
    }
}
