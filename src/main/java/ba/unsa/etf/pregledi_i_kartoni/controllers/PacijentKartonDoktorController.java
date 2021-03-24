package ba.unsa.etf.pregledi_i_kartoni.controllers;

import ba.unsa.etf.pregledi_i_kartoni.models.*;
import ba.unsa.etf.pregledi_i_kartoni.services.PacijentKartonDoktorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class PacijentKartonDoktorController {

    private final PacijentKartonDoktorService pacijentDoktorService;

    private final List<PacijentKartonDoktor> pacijentiDoktori;


    private final Doktor doktor1 = new Doktor("ImeDoktora1", "PrezimeDoktora1", new Date(), "AdresaDoktora1", "061-123-123", "nekimaildoktora1@gmail.com");
    private final KartonPacijent pacijent1 = new KartonPacijent( "Ime1", "Prezime1", new Date(), "Adresa1", "061-456-456", "nekimailp1@gmail.com",
            "zenski", 175.2, 68.3, "0+", "/", "/");

    private final Doktor doktor2 = new Doktor("ImeDoktora2", "PrezimeDoktora2", new Date(), "AdresaDoktora2", "061-723-723", "nekimaildoktora2@gmail.com");
    private final KartonPacijent pacijent2 = new KartonPacijent( "Ime2", "Prezime2", new Date(), "Adresa2", "061-323-323", "nekimailp2@gmail.com",
            "zenski", 165.4, 56.3, "A-", "/", "/");


    private final PacijentKartonDoktor pd1 = new PacijentKartonDoktor(doktor1, pacijent1);
    private final PacijentKartonDoktor pd2 = new PacijentKartonDoktor(doktor2, pacijent2);

    @PostMapping("/pohraniPacijentiDoktori")
    public @ResponseBody String spasiListuPacijentiDoktori() {
        pacijentiDoktori.add(pd1);
        pacijentiDoktori.add(pd2);
        return pacijentDoktorService.spasiPacijentDoktor(pacijentiDoktori);
    }


}

