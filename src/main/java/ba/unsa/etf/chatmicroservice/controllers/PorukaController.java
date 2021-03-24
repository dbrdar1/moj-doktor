package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.exceptions.DBObjectNotFoundException;
import ba.unsa.etf.chatmicroservice.models.Poruka;
import ba.unsa.etf.chatmicroservice.repositories.PorukaRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class PorukaController {

    private final PorukaRepository porukaRepository;

    @GetMapping("/poruke")
    List<Poruka> all() {
        return porukaRepository.findAll();
    }

    @GetMapping("/poruke/{id}")
    Poruka one(@PathVariable Long id) {
        String errorMessage = "Objekat sa zadanim ID-jem ne postoji.";
        return porukaRepository.findById(id)
                .orElseThrow(() -> new DBObjectNotFoundException(errorMessage));
    }
}

