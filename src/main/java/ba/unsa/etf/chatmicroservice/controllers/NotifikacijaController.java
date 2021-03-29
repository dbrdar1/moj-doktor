package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.models.Notifikacija;
import ba.unsa.etf.chatmicroservice.repositories.NotifikacijaRepository;
import ba.unsa.etf.chatmicroservice.requests.DodajNotifikacijuRequest;
import ba.unsa.etf.chatmicroservice.responses.NotifikacijaResponse;
import ba.unsa.etf.chatmicroservice.responses.NotifikacijeKorisnikaResponse;
import ba.unsa.etf.chatmicroservice.responses.Response;
import ba.unsa.etf.chatmicroservice.services.NotifikacijaService;
import lombok.AllArgsConstructor;
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

    @GetMapping("/notifikacije")
    List<Notifikacija> all() {
        return notifikacijaRepository.findAll();
    }

    @GetMapping("/notifikacije/{id}")
    public ResponseEntity<NotifikacijaResponse> dajNotifikaciju(@PathVariable Long id){
        NotifikacijaResponse notifikacijaResponse = notifikacijaService.dajNotifikaciju(id);
        return ResponseEntity.ok(notifikacijaResponse);
    }

    @GetMapping("/notifikacije-korisnika/{idKorisnika}")
    public ResponseEntity<NotifikacijeKorisnikaResponse> dajNotifikacijeKorisnika(@PathVariable Long idKorisnika){
        NotifikacijeKorisnikaResponse notifikacijeKorisnikaResponse = notifikacijaService.dajNotifikacijeKorisnika(idKorisnika);
        return ResponseEntity.ok(notifikacijeKorisnikaResponse);
    }

    @PostMapping("/dodaj-notifikaciju")
    public ResponseEntity<Response> dodajNotifikaciju(@RequestBody DodajNotifikacijuRequest dodajNotifikacijuRequest){
        Response response = notifikacijaService.dodajNotifikaciju(dodajNotifikacijuRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/notifikacije/{id}")
    public ResponseEntity<Response> obrisiNotifikaciju(@PathVariable Long id) {
        Response response = notifikacijaService.obrisiNotifikaciju(id);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleNoSuchElementFoundException(ConstraintViolationException exception) {
        return new Response(exception.getConstraintViolations().toString(),500);
    }
}

