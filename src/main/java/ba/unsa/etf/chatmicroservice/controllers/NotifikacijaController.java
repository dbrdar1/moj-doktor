package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.exceptions.DBObjectNotFoundException;
import ba.unsa.etf.chatmicroservice.models.Notifikacija;
import ba.unsa.etf.chatmicroservice.repositories.NotifikacijaRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class NotifikacijaController {

    private final NotifikacijaRepository notifikacijaRepository;

    @GetMapping("/notifikacije")
    List<Notifikacija> all() {
        return notifikacijaRepository.findAll();
    }

    @GetMapping("/notifikacije/{id}")
    Notifikacija one(@PathVariable Long id) {
        String errorMessage = "Objekat sa zadanim ID-jem ne postoji.";
        return notifikacijaRepository.findById(id)
                .orElseThrow(() -> new DBObjectNotFoundException(errorMessage));
    }

    @PostMapping("/notifikacije")
    Notifikacija newNotifikacija(@RequestBody Notifikacija notifikacija) {
        return notifikacijaRepository.save(notifikacija);
    }
}

