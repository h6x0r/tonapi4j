package org.ton.sync.methods;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ton.schema.traces.Trace;
import org.ton.sync.TonapiTestBase;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestTraceMethod extends TonapiTestBase {

    private static final String TRACE_ID_HEX = "bc076562736912c22e0443ecbc4773bd798d0dc0527071bce75785813e26de54";
    private static final String TRACE_ID_BASE64 = "VdUG1YMbYqrinFDR4QR7j5CfGQ2O75m34bvxUKvmn00=";
    private static final String BOC = "te6cckECGAEAAxEABAgAAUyXARUXFgEU/wD0pBP0vPLICwICASADEAIBSAQHAubQAdDTAyFxsJJfBOAi10nBIJJfBOAC0x8hghBwbHVnvSKCEGRzdHK9sJJfBeAD+kAwIPpEAcjKB8v/ydDtRNCBAUDXIfQEMFyBAQj0Cm+hMbOSXwfgBdM/yCWCEHBsdWe6kjgw4w0DghBkc3RyupJfBuMNBQYAeAH6APQEMPgnbyIwUAqhIb7y4FCCEHBsdWeDHrFwgBhQBMsFJs8WWPoCGfQAy2kXyx9SYMs/IMmAQPsABgCKUASBAQj0WTDtRNCBAUDXIMgBzxb0AMntVAFysI4jghBkc3Rygx6xcIAYUAXLBVADzxYj+gITy2rLH8s/yYBA+wCSXwPiAgEgCA8CASAJDgIBWAoLAD2ynftRNCBAUDXIfQEMALIygfL/8nQAYEBCPQKb6ExgAgEgDA0AGa3OdqJoQCBrkOuF/8AAGa8d9qJoQBBrkOuFj8AAEbjJftRNDXCx+ABZvSQrb2omhAgKBrkPoCGEcNQICEekk30pkQzmkD6f+YN4EoAbeBAUiYcVnzGEBPjygwjXGCDTH9Mf0x8C+CO78mTtRNDTH9Mf0//0BNFRQ7ryoVFRuvKiBfkBVBBk+RDyo/gAJKTIyx9SQMsfUjDL/1IQ9ADJ7VT4DwHTByHAAJ9sUZMg10qW0wfUAvsA6DDgIcAB4wAhwALjAAHAA5Ew4w0DpMjLHxLLH8v/ERITFABu0gf6ANTUIvkABcjKBxXL/8nQd3SAGMjLBcsCIs8WUAX6AhTLaxLMzMlz+wDIQBSBAQj0UfKnAgBwgQEI1xj6ANM/yFQgR4EBCPRR8qeCEG5vdGVwdIAYyMsFywJQBs8WUAT6AhTLahLLH8s/yXP7AAIAbIEBCNcY+gDTPzBSJIEBCPRZ8qeCEGRzdHJwdIAYyMsFywJQBc8WUAP6AhPLassfEss/yXP7AAAK9ADJ7VQAUQAAAAAAAAAqgqCyVD0G/sCqyVLp7HOL5WqxtgJ/wMGqgXrhS00e0vtAAQAXAAYAAAB5S1or";

    @Test
    public void testGetTrace() {
        Trace responseHex = tonapi.getTraces().getTrace(TRACE_ID_HEX);
        assertNotNull(responseHex);

        // Test with Base64 trace ID
        Trace responseBase64 = tonapi.getTraces().getTrace(TRACE_ID_BASE64);
        assertNotNull(responseBase64);
    }

    @Test
    @Disabled
    public void testEmulate() {
        Map<String, Object> body = new HashMap<>();
        body.put("boc", BOC);

        Trace response = tonapi.getTraces().emulate(body, null);
        assertNotNull(response);
    }
}
