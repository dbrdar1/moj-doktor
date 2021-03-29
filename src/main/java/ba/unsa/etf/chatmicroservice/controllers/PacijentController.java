package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.models.Pacijent;
import ba.unsa.etf.chatmicroservice.repositories.PacijentRepository;
import ba.unsa.etf.chatmicroservice.responses.PacijentResponse;
import ba.unsa.etf.chatmicroservice.services.PacijentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class PacijentController {

    private final PacijentService pacijentService;
    private final PacijentRepository pacijentRepository;

    @GetMapping("/pacijenti")
    List<Pacijent> all() {
        return pacijentRepository.findAll();
    }

    @GetMapping("/pacijenti/{id}")
    public ResponseEntity<PacijentResponse> dajPacijenta(@PathVariable Long id) {
        PacijentResponse pacijentResponse = pacijentService.dajPacijenta(id);
        return ResponseEntity.ok(pacijentResponse);
    }
}

