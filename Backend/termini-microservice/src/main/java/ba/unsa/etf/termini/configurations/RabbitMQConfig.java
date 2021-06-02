package ba.unsa.etf.termini.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue terminiDodavanje() {
        return new Queue("terminiDodavanje");
    }

    @Bean
    public Queue terminiBrisanje() {
        return new Queue("terminiBrisanje");
    }

    @Bean
    public NoviKorisnikMessageReceiver receiver() {
        return new NoviKorisnikMessageReceiver();
    }
}
