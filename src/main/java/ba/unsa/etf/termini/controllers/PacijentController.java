package ba.unsa.etf.termini.controllers;

import ba.unsa.etf.termini.models.Pacijent;
import ba.unsa.etf.termini.models.Korisnik;
import ba.unsa.etf.termini.repositories.KorisnikRepository;
import ba.unsa.etf.termini.services.PacijentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class PacijentController {
    private final PacijentService pacijentService;
    private KorisnikRepository korisnikRepository;

    private final Pacijent k2 = new Pacijent(
            "Jusuf",
            "Delalic",
            new Date(),
            "NekaAdresa","061456321",
            "jusuf@mail.com");

    private final List<Pacijent> pacijenti;


    /*@GetMapping("/pohraniPacijente")
    public @ResponseBody
    String spasiListuKorisnika(){
        pacijenti.add(k2);
        return  pacijentService.spasiPacijente(pacijenti);
    }*/
}
