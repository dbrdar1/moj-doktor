package ba.unsa.etf.pregledi_i_kartoni.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public NoviKorisnikMessageReceiver receiver() {
        return new NoviKorisnikMessageReceiver();
    }

    @Bean
    public TerminiMessageReceiver terminiMessageReceiver() {
        return new TerminiMessageReceiver();
    }
}