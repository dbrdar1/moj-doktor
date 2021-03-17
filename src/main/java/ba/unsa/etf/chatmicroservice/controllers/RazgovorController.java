package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.models.Korisnik;
import ba.unsa.etf.chatmicroservice.models.Razgovor;
import ba.unsa.etf.chatmicroservice.services.RazgovorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class RazgovorController {

    private final RazgovorService razgovorService;

    private final List<Razgovor> razgovori;
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

    @GetMapping("/pohraniRazgovore")
    public @ResponseBody String spasiListuRazgovora() {
        razgovori.add(razgovor1);
        return razgovorService.spasiRazgovore(razgovori);
    }
}

