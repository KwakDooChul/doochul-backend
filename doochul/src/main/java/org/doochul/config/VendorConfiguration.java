package org.doochul.config;

import java.util.Set;
import org.doochul.application.LoginClients;
import org.doochul.application.client.LoginClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VendorConfiguration {
 
    @Bean
    public LoginClients loginClients(final Set<LoginClient> clients) {
        return new LoginClients(clients);
    }
}
