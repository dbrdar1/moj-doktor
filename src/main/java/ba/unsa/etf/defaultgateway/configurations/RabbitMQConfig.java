package ba.unsa.etf.defaultgateway.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queue1() {
        return new Queue("korisnikQueue1");
    }

    @Bean
    public Queue queue2() {
        return new Queue("korisnikQueue2");
    }

    @Bean
    public Queue queue3() {
        return new Queue("korisnikQueue3");
    }

    @Bean
    public Queue queue4() {
        return new Queue("korisnikQueue4");
    }
}
