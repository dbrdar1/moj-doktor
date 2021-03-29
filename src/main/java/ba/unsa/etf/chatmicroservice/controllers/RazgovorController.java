package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.models.Razgovor;
import ba.unsa.etf.chatmicroservice.repositories.RazgovorRepository;
import ba.unsa.etf.chatmicroservice.requests.DodajNotifikacijuRequest;
import ba.unsa.etf.chatmicroservice.requests.DodajRazgovorRequest;
import ba.unsa.etf.chatmicroservice.responses.NotifikacijeKorisnikaResponse;
import ba.unsa.etf.chatmicroservice.responses.RazgovoriKorisnikaResponse;
import ba.unsa.etf.chatmicroservice.responses.Response;
import ba.unsa.etf.chatmicroservice.services.RazgovorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@AllArgsConstructor
@RestController
public class RazgovorController {

    private final RazgovorRepository razgovorRepository;
    private final RazgovorService razgovorService;

    @GetMapping("/razgovori")
    List<Razgovor> all() {
        return razgovorRepository.findAll();
    }

    @GetMapping("/razgovori/{id}")
    public ResponseEntity<Razgovor> dajRazgovor(@PathVariable Long id){
        Razgovor razgovor = razgovorService.dajRazgovor(id);
        return ResponseEntity.ok(razgovor);
    }

    @GetMapping("/razgovori-prvog-korisnika/{idKorisnika}")
    public ResponseEntity<RazgovoriKorisnikaResponse> dajRazgovorePrvogKorisnika(@PathVariable Long idKorisnika){
        RazgovoriKorisnikaResponse razgovoriKorisnikaResponse = razgovorService.dajRazgovoreKorisnika1(idKorisnika);
        return ResponseEntity.ok(razgovoriKorisnikaResponse);
    }

    @GetMapping("/razgovori-drugog-korisnika/{idKorisnika}")
    public ResponseEntity<RazgovoriKorisnikaResponse> dajRazgovoreDrugogKorisnika(@PathVariable Long idKorisnika){
        RazgovoriKorisnikaResponse razgovoriKorisnikaResponse = razgovorService.dajRazgovoreKorisnika2(idKorisnika);
        return ResponseEntity.ok(razgovoriKorisnikaResponse);
    }

    @PostMapping("/dodaj-razgovor")
    public ResponseEntity<Response> dodajRazgovor(@RequestBody DodajRazgovorRequest dodajRazgovorRequest){
        Response response = razgovorService.dodajRazgovor(dodajRazgovorRequest);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleNoSuchElementFoundException(ConstraintViolationException exception) {
        return new Response(exception.getConstraintViolations().toString(),500);
    }
}

