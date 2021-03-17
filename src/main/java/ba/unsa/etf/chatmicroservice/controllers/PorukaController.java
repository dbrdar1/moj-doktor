package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.models.Korisnik;
import ba.unsa.etf.chatmicroservice.models.Poruka;
import ba.unsa.etf.chatmicroservice.models.Razgovor;
import ba.unsa.etf.chatmicroservice.services.PorukaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class PorukaController {

    private final PorukaService porukaService;

    private final List<Poruka> poruke;
    private final Korisnik korisnik1 = new Korisnik(
            "Samra",
            "Pusina",
            new Date(),
            "NekaAdresa",
            "samra@mail",
            "061456321"
    );
    private final Korisnik korisnik2 = new Korisnik(
            "Esmina",
            "Radusic",
            new Date(),
            "NekaAdresa",
            "esmina@mail",
            "061456321"
    );

    private final Razgovor razgovor1 = new Razgovor(korisnik1, korisnik2);

    private final Poruka poruka1 = new Poruka("eee", 1, 1, new Date(), "15:20", razgovor1);

    @GetMapping("/pohraniPoruke")
    public @ResponseBody String spasiListuPoruka() {
        poruke.add(poruka1);
        return porukaService.spasiPoruke(poruke);
    }
}

