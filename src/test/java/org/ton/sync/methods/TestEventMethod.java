package org.ton.sync.methods;

import org.junit.jupiter.api.Test;
import org.ton.exception.TONAPIError;
import org.ton.schema.events.Event;
import org.ton.sync.TonapiTestBase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.ACCEPT_LANGUAGE_EN;
import static org.ton.utils.Constants.EVENT_ID_HEX;

public class TestEventMethod extends TonapiTestBase {

    @Test
    public void testGetEventHex() throws TONAPIError {
        Event response = tonapi.getEvents().getEvent(EVENT_ID_HEX, ACCEPT_LANGUAGE_EN);
        assertNotNull(response);
    }
}
