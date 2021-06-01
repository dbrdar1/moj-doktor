package ba.unsa.etf.pregledi_i_kartoni.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public NoviKorisnikMessageReceiver receiver() {
        return new NoviKorisnikMessageReceiver();
    }

    @Bean
    public TerminiDodavanjeMessageReceiver terminiDodavanjeMessageReceiver() {
        return new TerminiDodavanjeMessageReceiver();
    }

    @Bean
    public TerminiBrisanjeMessageReceiver terminiBrisanjeMessageReceiver() {
        return new TerminiBrisanjeMessageReceiver();
    }
}