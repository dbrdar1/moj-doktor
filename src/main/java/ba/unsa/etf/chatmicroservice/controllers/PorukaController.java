package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.models.Poruka;
import ba.unsa.etf.chatmicroservice.projections.PorukaProjection;
import ba.unsa.etf.chatmicroservice.repositories.PorukaRepository;
import ba.unsa.etf.chatmicroservice.requests.DodajPorukuRequest;
import ba.unsa.etf.chatmicroservice.responses.NotifikacijaResponse;
import ba.unsa.etf.chatmicroservice.responses.PorukaResponse;
import ba.unsa.etf.chatmicroservice.responses.PorukePosiljaocaIPrimaocaResponse;
import ba.unsa.etf.chatmicroservice.responses.Response;
import ba.unsa.etf.chatmicroservice.services.PorukaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<PorukaResponse> dajPoruku(@PathVariable Long id){
        PorukaResponse porukaResponse = porukaService.dajPoruku(id);
        return ResponseEntity.ok(porukaResponse);
    }

    @GetMapping("/poruke-po-posiljaocu-i-primaocu")
    public ResponseEntity<PorukePosiljaocaIPrimaocaResponse> dajPorukePoPosiljaocuIPrimaocu
            (@RequestParam Long idPosiljaoca, @RequestParam Long idPrimaoca) {
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleNoSuchElementFoundException(ConstraintViolationException exception) {
        StringBuilder message = new StringBuilder();
        List<String> messages = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toList());
        for (int i = 0; i < messages.size(); i++)
            if (i < messages.size() - 1) message.append(messages.get(i)).append("; ");
            else message.append(messages.get(i));
        return new Response(message.toString(),400);
    }
}

