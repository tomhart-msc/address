package io.tomhart.address.parsing;

import static org.junit.jupiter.api.Assertions.*;

import io.tomhart.address.model.Page;
import io.tomhart.address.model.User;
import io.tomhart.address.model.UserDetails;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class PagerDutyParserTest {
    private static final Path RESOURCES = Paths.get("src", "test", "resources");

    @Test
    public void canParseAListOfUsers() {
        final PagerDutyParser sut = new PagerDutyParser();
        final String input = userListInput();
        System.out.println(input);
        assertNotNull(input);
        final Page<User> page = sut.parseUserPage(input);
        assertEquals(25, page.getResults().size());
        assertEquals(0, page.getOffset());
        assertTrue(page.getMore());
    }

    @Test
    public void canParseUserDetails() {
        final PagerDutyParser sut = new PagerDutyParser();
        final UserDetails actual = sut.parseUserDetails(userDetailsInput(), contactsInput());
        final UserDetails expected =
                new UserDetails(
                        "PLOASXQ",
                        "Alan B. Shepard, Jr.",
                        "+1-1115550001",
                        "alan.shepard@nasa.example",
                        "+1-1115550002");
        assertEquals(expected, actual);
    }

    @Test
    public void canHandleMissingContactDetails() {
        final PagerDutyParser sut = new PagerDutyParser();
        final UserDetails actual = sut.parseUserDetails(userDetailsInput(), noContactsInput());
        final UserDetails expected =
                new UserDetails("PLOASXQ", "Alan B. Shepard, Jr.", "none", "none", "none");
        assertEquals(expected, actual);
    }

    private String userListInput() {
        return inputString("userlist.json");
    }

    private String userDetailsInput() {
        return inputString("user-alan.json");
    }

    private String contactsInput() {
        return inputString("contacts-alan.json");
    }

    private String noContactsInput() {
        return inputString("contacts-empty.json");
    }

    private String inputString(final String fileName) {
        try {
            final Path inputPath = RESOURCES.resolve(fileName);
            final InputStream stream = Files.newInputStream(inputPath);
            return new BufferedReader(new InputStreamReader(stream))
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
