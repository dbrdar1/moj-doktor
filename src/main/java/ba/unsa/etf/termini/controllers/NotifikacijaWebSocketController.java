package ba.unsa.etf.termini.controllers;

import ba.unsa.etf.termini.Requests.DodajNotifikacijuRequest;
import ba.unsa.etf.termini.models.NotifikacijaWebSocket;
import ba.unsa.etf.termini.services.NotifikacijaService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Controller
public class NotifikacijaWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private NotifikacijaService notifikacijaService;

    @MessageMapping("/notifikacije")
    public void processMessage(@Payload NotifikacijaWebSocket notifikacijaWebSocket) {
        messagingTemplate.convertAndSendToUser(
                String.valueOf(notifikacijaWebSocket.getRecipientId()),
                "/queue/notifikacije",
                notifikacijaWebSocket
        );

    }
}
