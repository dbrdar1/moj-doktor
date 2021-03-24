package ba.unsa.etf.pregledi_i_kartoni.controllers;

import ba.unsa.etf.pregledi_i_kartoni.models.KartonPacijent;
import ba.unsa.etf.pregledi_i_kartoni.models.Korisnik;
import ba.unsa.etf.pregledi_i_kartoni.models.Doktor;
import ba.unsa.etf.pregledi_i_kartoni.models.PacijentKartonDoktor;
import ba.unsa.etf.pregledi_i_kartoni.services.KartonPacijentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class KartonPacijentController {

    private final KartonPacijentService kartonPacijentService;

    private final List<KartonPacijent> pacijenti;


    private final KartonPacijent pacijent1 = new KartonPacijent( "Ime1", "Prezime1", new Date(), "Adresa1", "061-456-456", "nekimailp1@gmail.com",
            "zenski", 175.2, 68.3, "0+", "/", "/");

    private final KartonPacijent pacijent2 = new KartonPacijent( "Ime2", "Prezime2", new Date(), "Adresa2", "061-323-323", "nekimailp2@gmail.com",
            "zenski", 165.4, 56.3, "A-", "/", "/");

    @GetMapping("/dajPacijenta")
    public ResponseEntity<KartonPacijent> getPacijent (@RequestParam(value = "id") Long idPacijenta){
        KartonPacijent trazeniPacijent = kartonPacijentService.getPacijentById(idPacijenta);
        return ResponseEntity.ok(trazeniPacijent);
    }

    @GetMapping("/dajSvePacijente")
    public ResponseEntity<List<KartonPacijent>> getSviPacijenti (){
        List<KartonPacijent> trazeniPacijenti = kartonPacijentService.getAllPacijent();
        return ResponseEntity.ok(trazeniPacijenti);
    }

    @PostMapping("/pohraniPacijente")
    public @ResponseBody String pohraniPacijente() {
        pacijenti.add(pacijent1);
        pacijenti.add(pacijent2);
        return kartonPacijentService.pohraniPacijente(pacijenti);
    }


}

