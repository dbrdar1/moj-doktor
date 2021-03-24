package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.exceptions.DBObjectNotFoundException;
import ba.unsa.etf.chatmicroservice.models.Doktor;
import ba.unsa.etf.chatmicroservice.models.Korisnik;
import ba.unsa.etf.chatmicroservice.repositories.DoktorRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class DoktorController {

    private final DoktorRepository doktorRepository;

    @GetMapping("/doktori")
    List<Doktor> all() {
        return doktorRepository.findAll();
    }

    @GetMapping("/doktori/{id}")
    Korisnik one(@PathVariable Long id) {
        String errorMessage = "Objekat sa zadanim ID-jem ne postoji.";
        return doktorRepository.findById(id)
                .orElseThrow(() -> new DBObjectNotFoundException(errorMessage));
    }
}

