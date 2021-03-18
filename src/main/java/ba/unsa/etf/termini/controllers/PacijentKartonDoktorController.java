package ba.unsa.etf.termini.controllers;

import ba.unsa.etf.termini.models.Doktor;
import ba.unsa.etf.termini.models.Pacijent;
import ba.unsa.etf.termini.models.PacijentKartonDoktor;
import ba.unsa.etf.termini.services.PacijentKartonDoktorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
@AllArgsConstructor
@RestController
public class PacijentKartonDoktorController {

    private PacijentKartonDoktorService pacijentKartonDoktorService;

    private final Pacijent p1 = new Pacijent(
            "Samra",
            "Pusina",
            new Date(),
            "NekaAdresa",
            "061456321","samra@mail.com");
    private final Doktor d1 = new Doktor(
            "Dzavid",
            "Brdar",
            new Date(),
            "NekaAdresa",
            "061456321",
            "dzavid@mail.com",
            "Doktor opce prakse");

    /*@GetMapping("/pohraniPKD")
    public @ResponseBody
    String spasiVezu() {
        PacijentKartonDoktor pkd1=new PacijentKartonDoktor(d1,p1);
        return pacijentKartonDoktorService.spasiVezuDoktorPacijent(pkd1);
    }*/
}
