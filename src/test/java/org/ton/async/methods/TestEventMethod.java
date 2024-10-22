package org.ton.async.methods;

import org.junit.jupiter.api.Test;
import org.ton.async.AsyncTonapiTestBase;
import org.ton.exception.TONAPIError;
import org.ton.schema.events.Event;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.ton.utils.Constants.ACCEPT_LANGUAGE_EN;
import static org.ton.utils.Constants.EVENT_ID_HEX;

public class TestEventMethod extends AsyncTonapiTestBase {

    @Test
    public void testGetEventHex() throws TONAPIError, ExecutionException, InterruptedException {
        CompletableFuture<Event> future = tonapi.getEvents().getEvent(EVENT_ID_HEX, ACCEPT_LANGUAGE_EN);
        Event response = future.get();
        assertNotNull(response);
    }
}
