package io.tomhart.address.parsing;

import com.google.gson.Gson;
import io.tomhart.address.model.Page;
import io.tomhart.address.model.User;
import io.tomhart.address.model.UserDetails;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component("pagerDutyParser")
public class PagerDutyParser {
    private final Gson gson;

    public PagerDutyParser() {
        gson = new Gson();
    }

    @SuppressWarnings("unchecked")
    public Page<User> parseUserPage(final String input) {
        final Map map = gson.fromJson(input, Map.class);
        final List<Map> rawUsers = (List<Map>) map.get("users");
        final int offset = ((Double) map.get("offset")).intValue();
        final boolean more = (boolean) map.get("more");
        final List users =
                rawUsers.stream()
                        .map(m -> new User((String) m.get("id"), (String) m.get("name")))
                        .collect(Collectors.toList());
        return new Page<User>(offset, more, users);
    }

    @SuppressWarnings("unchecked")
    public UserDetails parseUserDetails(final String user, final String contacts) {
        final Map userMap = (Map) (gson.fromJson(user, Map.class).get("user"));
        final String id = (String) userMap.get("id");
        final String name = (String) userMap.get("name");

        final Map contactsMap = gson.fromJson(contacts, Map.class);
        final List<Map> contactList = (List<Map>) contactsMap.get("contact_methods");
        final String email = extractContact(contactList, "email_contact_method");
        final String sms = extractContact(contactList, "sms_contact_method");
        final String phone = extractContact(contactList, "phone_contact_method");
        return new UserDetails(id, name, phone, email, sms);
    }

    private String extractContact(final List<Map> contactList, final String type) {
        Optional<Map> maybeMap =
                contactList.stream().filter(m -> type.equals(m.get("type"))).findFirst();
        if (maybeMap.isEmpty()) {
            return "none";
        } else {
            return formatContact(maybeMap.get());
        }
    }

    private String formatContact(final Map map) {
        if (map.containsKey("country_code")) return phoneFromMap(map);
        return emailFromMap(map);
    }

    private String emailFromMap(final Map map) {
        return (String) map.get("address");
    }

    private String phoneFromMap(final Map map) {
        final int areaCode = ((Double) map.get("country_code")).intValue();
        final String phone = (String) map.get("address");
        return String.format("+%d-%s", areaCode, phone);
    }
}
