package ba.unsa.etf.chatmicroservice.config;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@RabbitListener(queues = "addNotificationTerminiToChat")
public class AddNotificationMessageReceiver {

    @RabbitHandler
    public void receive(String receivedMessage) {
        System.out.println("Received: " + receivedMessage);
        /*String post_url = "http://localhost:8084/dodaj-notifikaciju";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject(receivedMessage);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toString(), headers);
        restTemplate.postForObject(post_url, httpEntity, String.class);*/
    }
}
