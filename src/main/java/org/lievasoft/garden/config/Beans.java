package org.lievasoft.garden.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Configuration
public class Beans {

    @Bean
    public KeyHolder keyHolder() {
        return new GeneratedKeyHolder();
    }
}
