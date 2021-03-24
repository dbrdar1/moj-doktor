package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.exceptions.DBObjectNotFoundException;
import ba.unsa.etf.chatmicroservice.models.Korisnik;
import ba.unsa.etf.chatmicroservice.models.Pacijent;
import ba.unsa.etf.chatmicroservice.repositories.PacijentRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class PacijentController {

    private final PacijentRepository pacijentRepository;

    @GetMapping("/pacijenti")
    List<Pacijent> all() {
        return pacijentRepository.findAll();
    }

    @GetMapping("/pacijenti/{id}")
    Korisnik one(@PathVariable Long id) {
        String errorMessage = "Objekat sa zadanim ID-jem ne postoji.";
        return pacijentRepository.findById(id)
                .orElseThrow(() -> new DBObjectNotFoundException(errorMessage));
    }
}

