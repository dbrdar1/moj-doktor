package ba.unsa.etf.chatmicroservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue addNotificationChatToTermini() {
        return new Queue("addNotificationChatToTermini");
    }

    @Bean
    public AddNotificationMessageReceiver receiver() {
        return new AddNotificationMessageReceiver();
    }
}
