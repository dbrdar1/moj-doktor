package ba.unsa.etf.chatmicroservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public NoviKorisnikMessageReceiver noviKorisnikMessageReceiver() {
        return new NoviKorisnikMessageReceiver();
    }
}
