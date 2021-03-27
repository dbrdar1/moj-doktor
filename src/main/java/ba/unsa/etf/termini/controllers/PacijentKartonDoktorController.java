package ba.unsa.etf.termini.controllers;

import ba.unsa.etf.termini.Requests.DodajPacijentKartonDoktorRequest;
import ba.unsa.etf.termini.Responses.NotifikacijeKorisnikaResponse;
import ba.unsa.etf.termini.Responses.PacijentKartonDoktorResponse;
import ba.unsa.etf.termini.Responses.Response;
import ba.unsa.etf.termini.models.Doktor;
import ba.unsa.etf.termini.models.Pacijent;
import ba.unsa.etf.termini.models.PacijentKartonDoktor;
import ba.unsa.etf.termini.services.PacijentKartonDoktorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Date;
@AllArgsConstructor
@RestController
public class PacijentKartonDoktorController {

    private PacijentKartonDoktorService pacijentKartonDoktorService;

    private final Pacijent p1 = new Pacijent(
            "Samra",
            "Pusina",
            new Date(),
            "NekaAdresa",
            "061456321","samra@mail.com");
    private final Doktor d1 = new Doktor(
            "Dzavid",
            "Brdar",
            new Date(),
            "NekaAdresa",
            "061456321",
            "dzavid@mail.com",
            "Doktor opce prakse");

    @PostMapping("/dodaj-vezu-pkd")
    @ResponseBody
    public ResponseEntity<Response>  spasiVezuDoktorPacijent(@RequestBody DodajPacijentKartonDoktorRequest dodajPacijentKartonDoktorRequest) {
        Response response = pacijentKartonDoktorService.spasiVezuDoktorPacijent(dodajPacijentKartonDoktorRequest.getPacijentId(),dodajPacijentKartonDoktorRequest.getDoktorId());
        return ResponseEntity.ok(response);    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleNoSuchElementFoundException(
            ConstraintViolationException exception
    ) {
        return new Response(exception.getConstraintViolations().toString(),500);
    }
}
