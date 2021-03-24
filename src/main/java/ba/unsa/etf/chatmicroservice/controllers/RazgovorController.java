package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.exceptions.DBObjectNotFoundException;
import ba.unsa.etf.chatmicroservice.models.Korisnik;
import ba.unsa.etf.chatmicroservice.models.Poruka;
import ba.unsa.etf.chatmicroservice.models.Razgovor;
import ba.unsa.etf.chatmicroservice.repositories.PorukaRepository;
import ba.unsa.etf.chatmicroservice.repositories.RazgovorRepository;
import ba.unsa.etf.chatmicroservice.services.RazgovorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class RazgovorController {

    private final RazgovorRepository razgovorRepository;

    @GetMapping("/razgovori")
    List<Razgovor> all() {
        return razgovorRepository.findAll();
    }

    @GetMapping("/razgovori/{id}")
    Razgovor one(@PathVariable Long id) {
        String errorMessage = "Objekat sa zadanim ID-jem ne postoji.";
        return razgovorRepository.findById(id)
                .orElseThrow(() -> new DBObjectNotFoundException(errorMessage));
    }
}

