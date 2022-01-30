package io.tomhart.address.model;

import java.util.Objects;

public class UserDetails {
    private final String id;
    private final String name;
    private final String phone;
    private final String email;
    private final String sms;

    public UserDetails(String id, String name, String phone, String email, String sms) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.sms = sms;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getSms() {
        return sms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails that = (UserDetails) o;
        return id.equals(that.id)
                && name.equals(that.name)
                && Objects.equals(phone, that.phone)
                && Objects.equals(email, that.email)
                && Objects.equals(sms, that.sms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, email, sms);
    }

    @Override
    public String toString() {
        return "UserDetails{"
                + "id='"
                + id
                + '\''
                + ", name='"
                + name
                + '\''
                + ", phone='"
                + phone
                + '\''
                + ", email='"
                + email
                + '\''
                + ", sms='"
                + sms
                + '\''
                + '}';
    }
}
