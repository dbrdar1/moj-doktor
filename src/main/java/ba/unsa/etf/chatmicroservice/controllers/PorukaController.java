package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.models.Poruka;
import ba.unsa.etf.chatmicroservice.repositories.PorukaRepository;
import ba.unsa.etf.chatmicroservice.services.PorukaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}

