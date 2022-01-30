package io.tomhart.address.service;

import io.tomhart.address.model.Page;
import io.tomhart.address.model.User;
import io.tomhart.address.model.UserDetails;
import io.tomhart.address.pagerduty.Client;
import io.tomhart.address.parsing.PagerDutyParser;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final Client client;
    private final PagerDutyParser parser;

    @Autowired
    public UserService(final Client client, final PagerDutyParser parser) {
        this.client = client;
        this.parser = parser;
    }

    public Page<User> listUsers(int offset) {
        try {
            return parser.parseUserPage(client.listUsers(offset));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDetails getUser(final String id) {
        try {
            return parser.parseUserDetails(client.getUser(id), client.getUserContactMethods(id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
