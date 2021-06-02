package ba.unsa.etf.doktordetalji.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public NoviKorisnikMessageReceiver receiver() {
        return new NoviKorisnikMessageReceiver();
    }
}
