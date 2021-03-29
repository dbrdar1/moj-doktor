package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.models.Doktor;
import ba.unsa.etf.chatmicroservice.models.Korisnik;
import ba.unsa.etf.chatmicroservice.repositories.DoktorRepository;
import ba.unsa.etf.chatmicroservice.responses.DoktorResponse;
import ba.unsa.etf.chatmicroservice.services.DoktorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class DoktorController {

    private final DoktorRepository doktorRepository;
    private final DoktorService doktorService;

    @GetMapping("/doktori")
    List<Doktor> all() {
        return doktorRepository.findAll();
    }

    @GetMapping("/doktori/{id}")
    public ResponseEntity<DoktorResponse> dajDoktora(@PathVariable Long id){
        DoktorResponse doktorResponse = doktorService.dajDoktora(id);
        return ResponseEntity.ok(doktorResponse);
    }
}

