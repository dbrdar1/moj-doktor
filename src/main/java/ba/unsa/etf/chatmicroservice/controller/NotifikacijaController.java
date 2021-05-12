package ba.unsa.etf.chatmicroservice.controller;

import ba.unsa.etf.chatmicroservice.exception.ResourceNotFoundException;
import ba.unsa.etf.chatmicroservice.model.Notifikacija;
import ba.unsa.etf.chatmicroservice.repository.NotifikacijaRepository;
import ba.unsa.etf.chatmicroservice.request.DodajNotifikacijuRequest;
import ba.unsa.etf.chatmicroservice.response.NotifikacijaResponse;
import ba.unsa.etf.chatmicroservice.response.NotifikacijeKorisnikaResponse;
import ba.unsa.etf.chatmicroservice.response.Response;
import ba.unsa.etf.chatmicroservice.service.NotifikacijaService;
import ba.unsa.etf.chatmicroservice.util.ErrorHandlingHelper;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@AllArgsConstructor
@RestController
public class NotifikacijaController {

    private final NotifikacijaRepository notifikacijaRepository;

    private final NotifikacijaService notifikacijaService;

    private final RabbitTemplate template;

    private final Queue queue;

    @GetMapping("/notifikacije")
    List<Notifikacija> all() {
        return notifikacijaRepository.findAll();
    }

    @GetMapping("/notifikacije/{id}")
    public ResponseEntity<NotifikacijaResponse> one(@PathVariable Long id) {
        NotifikacijaResponse notifikacijaResponse = notifikacijaService.dajNotifikaciju(id);
        return ResponseEntity.ok(notifikacijaResponse);
    }

    @GetMapping("/notifikacije-korisnika/{idKorisnika}")
    public ResponseEntity<NotifikacijeKorisnikaResponse> dajNotifikacijeKorisnika(@PathVariable Long idKorisnika) {
        NotifikacijeKorisnikaResponse notifikacijeKorisnikaResponse = notifikacijaService.dajNotifikacijeKorisnika(idKorisnika);
        return ResponseEntity.ok(notifikacijeKorisnikaResponse);
    }

    @PostMapping("/dodaj-notifikaciju")
    public ResponseEntity<Response> dodajNotifikaciju(@RequestBody DodajNotifikacijuRequest dodajNotifikacijuRequest) {
        Response response = notifikacijaService.dodajNotifikaciju(dodajNotifikacijuRequest);
        sendAddNotificationMessage(dodajNotifikacijuRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/notifikacije/{id}")
    public ResponseEntity<Response> obrisiNotifikaciju(@PathVariable Long id) {
        Response response = notifikacijaService.obrisiNotifikaciju(id);
        return ResponseEntity.ok(response);
    }

    public void sendAddNotificationMessage(DodajNotifikacijuRequest dodajNotifikacijuRequest) {
        JSONObject addNotificationMessageObject = new JSONObject();
        addNotificationMessageObject.put("naslov", dodajNotifikacijuRequest.getNaslov());
        addNotificationMessageObject.put("tekst", dodajNotifikacijuRequest.getTekst());
        addNotificationMessageObject.put("datum", "2020-02-22");
        addNotificationMessageObject.put("vrijeme", dodajNotifikacijuRequest.getVrijeme());
        addNotificationMessageObject.put("idKorisnika", dodajNotifikacijuRequest.getIdKorisnika());
        String message = addNotificationMessageObject.toString();
        this.template.convertAndSend(queue.getName(), message);
        System.out.println("Sent: " + message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleConstraintViolationException(ConstraintViolationException exception) {
        return ErrorHandlingHelper.handleConstraintViolationException(exception);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleEntityNotFoundException(ResourceNotFoundException exception) {
        return ErrorHandlingHelper.handleEntityNotFoundException(exception);
    }
}

