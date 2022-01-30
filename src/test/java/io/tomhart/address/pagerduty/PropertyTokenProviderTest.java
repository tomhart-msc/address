package io.tomhart.address.pagerduty;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class PropertyTokenProviderTest {

    @Test
    public void itProvides() {
        final String token = UUID.randomUUID().toString();
        assertEquals(token, new PropertyTokenProvider(token).get());
    }
}
