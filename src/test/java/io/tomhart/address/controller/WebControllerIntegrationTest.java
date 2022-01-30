package io.tomhart.address.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.tomhart.address.pagerduty.Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

/**
 * Test all web endpoints. In a larger application this would be broken into multiple test classes.
 */
@SpringBootTest
@Profile("test")
class WebControllerIntegrationTest {
    private static final Path RESOURCES = Paths.get("src", "test", "resources");

    @Autowired private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean private Client client;

    @Test
    public void canListUsers() throws Exception {
        when(client.getUser(anyString())).thenReturn(userDetailsInput());
        when(client.getUserContactMethods(anyString())).thenReturn(contactsInput());
        when(client.listUsers(anyInt())).thenReturn(userListInput());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(get("/api/users/list")).andExpect(status().isOk());
    }

    @Test
    public void getUser() throws Exception {
        when(client.getUser(anyString())).thenReturn(userDetailsInput());
        when(client.getUserContactMethods(anyString())).thenReturn(contactsInput());
        when(client.listUsers(anyInt())).thenReturn(userListInput());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(get("/api/users/user/abcd")).andExpect(status().isOk());
    }

    @Test
    public void getSlash() throws Exception {
        when(client.getUser(anyString())).thenReturn(userDetailsInput());
        when(client.getUserContactMethods(anyString())).thenReturn(contactsInput());
        when(client.listUsers(anyInt())).thenReturn(userListInput());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    public void getUserDetailsWeb() throws Exception {
        when(client.getUser(anyString())).thenReturn(userDetailsInput());
        when(client.getUserContactMethods(anyString())).thenReturn(contactsInput());
        when(client.listUsers(anyInt())).thenReturn(userListInput());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(get("/user/abcd")).andExpect(status().isOk());
    }

    @Test
    public void getUserDetailsError() throws Exception {
        when(client.getUser(anyString())).thenThrow(new IOException("testing"));
        when(client.getUserContactMethods(anyString())).thenThrow(new IOException("testing"));
        when(client.listUsers(anyInt())).thenThrow(new IOException("testing"));
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        assertThrows(
                NestedServletException.class, () -> mockMvc.perform(get("/api/users/user/abcd")));
    }

    @Test
    public void listUsersError() throws Exception {
        when(client.getUser(anyString())).thenThrow(new IOException("testing"));
        when(client.getUserContactMethods(anyString())).thenThrow(new IOException("testing"));
        when(client.listUsers(anyInt())).thenThrow(new IOException("testing"));
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        assertThrows(NestedServletException.class, () -> mockMvc.perform(get("/api/users/list")));
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
