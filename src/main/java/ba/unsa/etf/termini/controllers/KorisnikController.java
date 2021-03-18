package ba.unsa.etf.termini.controllers;

import ba.unsa.etf.termini.models.Korisnik;
import ba.unsa.etf.termini.services.KorisnikService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class KorisnikController {

    private final KorisnikService korisnikService;

    private final List<Korisnik> korisnici;
    private final Korisnik k1 = new Korisnik(
            "Samra",
            "Pusina",
            new Date(),
            "NekaAdresa","061456321",
            "samra@mail.com");

    private final Korisnik k2 = new Korisnik(
            "Esmina",
            "Radusic",
            new Date(),
            "NekaAdresa","061456321",
            "esmina@mail.com");

   /* @GetMapping("/pohraniKorisnike")
    public @ResponseBody String spasiListuKorisnika(){
        korisnici.add(k1);
        korisnici.add(k2);
        return  korisnikService.spasiKorisnike(korisnici);
    }*/
}

