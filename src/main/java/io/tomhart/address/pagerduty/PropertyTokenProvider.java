package io.tomhart.address.pagerduty;

import com.google.inject.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyTokenProvider implements Provider<String> {

    private final String value;

    @Autowired
    public PropertyTokenProvider(@Value("${PAGERDUTY_TOKEN}") final String value) {
        this.value = value;
    }

    public String get() {
        return this.value;
    }
}
