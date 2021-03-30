package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.models.Poruka;
import ba.unsa.etf.chatmicroservice.repositories.PorukaRepository;
import ba.unsa.etf.chatmicroservice.requests.DodajPorukuRequest;
import ba.unsa.etf.chatmicroservice.responses.PorukePosiljaocaIPrimaocaResponse;
import ba.unsa.etf.chatmicroservice.responses.Response;
import ba.unsa.etf.chatmicroservice.services.PorukaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@AllArgsConstructor
@RestController
public class PorukaController {

    private final PorukaRepository porukaRepository;
    private final PorukaService porukaService;

    @GetMapping("/poruke")
    List<Poruka> all() {
        return porukaRepository.findAll();
    }

    @GetMapping("/poruke/{id}")
    public ResponseEntity<Poruka> dajPoruku(@PathVariable Long id){
        Poruka poruka = porukaService.dajPoruku(id);
        return ResponseEntity.ok(poruka);
    }

    @GetMapping("/poruke-po-posiljaocu-i-primaocu/{idPosiljaoca}/{idPrimaoca}")
    public ResponseEntity<PorukePosiljaocaIPrimaocaResponse> dajPorukePoPosiljaocuIPrimaocu
            (@PathVariable Long idPosiljaoca, @PathVariable Long idPrimaoca) {
        PorukePosiljaocaIPrimaocaResponse porukePosiljaocaIPrimaocaResponse =
                porukaService.dajPorukePosiljaocaIPrimaoca(idPosiljaoca, idPrimaoca);
        return ResponseEntity.ok(porukePosiljaocaIPrimaocaResponse);
    }

    @PostMapping("/dodaj-poruku")
    public ResponseEntity<Response> dodajPoruku(@RequestBody DodajPorukuRequest dodajPorukuRequest) {
        Response response = porukaService.dodajPoruku(dodajPorukuRequest);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleNoSuchElementFoundException(ConstraintViolationException exception) {
        return new Response(exception.getConstraintViolations().toString(),500);
    }
}

