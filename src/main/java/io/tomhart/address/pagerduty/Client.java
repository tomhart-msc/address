package io.tomhart.address.pagerduty;

import java.io.IOException;

/**
 * An interface for a client to the PagerDuty API. Declared as an interface so that mocks can be
 * used during unit testing.
 */
public interface Client {
    String listUsers(int offset) throws IOException;

    String getUser(String id) throws IOException;

    String getUserContactMethods(String id) throws IOException;
}
