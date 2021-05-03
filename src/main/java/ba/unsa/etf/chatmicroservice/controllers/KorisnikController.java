package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.models.Korisnik;
import ba.unsa.etf.chatmicroservice.repositories.KorisnikRepository;
import ba.unsa.etf.chatmicroservice.responses.Response;
import ba.unsa.etf.chatmicroservice.services.KorisnikService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class KorisnikController {

    private final KorisnikRepository korisnikRepository;

    private final KorisnikService korisnikService;

    @GetMapping("/")
    public @ResponseBody String inicijalizacija(){
        return korisnikService.inicijalizirajBazu();
    }

    @GetMapping("/korisnici")
    List<Korisnik> all() {
        return korisnikRepository.findAll();
    }

    @GetMapping("/korisnici/{id}")
    Korisnik one(@PathVariable Long id) throws Exception {
        return korisnikRepository.findById(id)
                .orElseThrow(() -> new Exception("Korisnik nije pronađen"));
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

