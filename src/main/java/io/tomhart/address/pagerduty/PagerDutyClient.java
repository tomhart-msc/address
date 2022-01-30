package io.tomhart.address.pagerduty;

import com.google.inject.Provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/** The PagerDuty API Client encapsulates all network communication with the PagerDuty API. */
@Component("pagerDutyClient")
@Profile("default")
public class PagerDutyClient implements Client {

    public static final String LIST_USERS_ENDPOINT = "https://api.pagerduty.com/users";
    public static final String GET_USER_ENDPOINT_FORMAT = "https://api.pagerduty.com/users/%s";
    public static final String GET_USER_CONTACTS_ENDPOINT_FORMAT =
            "https://api.pagerduty.com/users/%s/contact_methods";
    private static final String AUTH_KEY = "Authorization";
    private static final String AUTH_VALUE_FORMAT = "Token token=%s";
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/json";
    private static final String ACCEPT_KEY = "Accept";
    private static final String ACCEPT_VALUE = "application/vnd.pagerduty+json;version=2";
    private final Provider<String> tokenProvider;

    @Autowired
    public PagerDutyClient(final Provider<String> tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public String listUsers(final int offset) throws IOException {
        return httpGetRequest(LIST_USERS_ENDPOINT + "?offset=" + offset, headers());
    }

    @Override
    public String getUser(final String id) throws IOException {
        return httpGetRequest(String.format(GET_USER_ENDPOINT_FORMAT, id), headers());
    }

    @Override
    public String getUserContactMethods(final String id) throws IOException {
        return httpGetRequest(String.format(GET_USER_CONTACTS_ENDPOINT_FORMAT, id), headers());
    }

    private Map<String, String> headers() {
        return Map.of(
                ACCEPT_KEY,
                ACCEPT_VALUE,
                CONTENT_TYPE_KEY,
                CONTENT_TYPE_VALUE,
                AUTH_KEY,
                String.format(AUTH_VALUE_FORMAT, this.tokenProvider.get()));
    }

    private String httpGetRequest(final String url, final Map<String, String> properties)
            throws IOException {
        URL url_obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) url_obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        properties.forEach((k, v) -> con.setRequestProperty(k, v));

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
