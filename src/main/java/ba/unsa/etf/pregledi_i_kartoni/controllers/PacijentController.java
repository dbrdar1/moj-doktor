package ba.unsa.etf.pregledi_i_kartoni.controllers;

import ba.unsa.etf.pregledi_i_kartoni.models.Pacijent;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajDoktoraRequest;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajPacijentaRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import ba.unsa.etf.pregledi_i_kartoni.services.PacijentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class PacijentController {

    private final PacijentService pacijentService;

    private final List<Pacijent> pacijenti;


    private final Pacijent pacijent1 = new Pacijent( "Ime1", "Prezime1", new Date(), "Adresa1", "061-456-456", "nekimailp1@gmail.com",
            "zenski", 175.2, 68.3, "0+", "/", "/");

    private final Pacijent pacijent2 = new Pacijent( "Ime2", "Prezime2", new Date(), "Adresa2", "061-323-323", "nekimailp2@gmail.com",
            "zenski", 165.4, 56.3, "A-", "/", "/");

    // prikaz pacijenta na osnovu id
    @GetMapping("/pacijent")
    public ResponseEntity<Pacijent> getPacijent (@RequestParam(value = "id") Long idPacijenta){
        Pacijent trazeniPacijent = pacijentService.getPacijentById(idPacijenta);
        return ResponseEntity.ok(trazeniPacijent);
    }

    // prikaz svih pacijenata
    @GetMapping("/svi-pacijenti")
    public ResponseEntity<List<Pacijent>> getSviPacijenti (){
        List<Pacijent> trazeniPacijenti = pacijentService.getAllPacijent();
        return ResponseEntity.ok(trazeniPacijenti);
    }


    // pohrana pacijenta
    @PostMapping("/dodaj-pacijenta")
    public ResponseEntity<Response> dodajPacijenta(@RequestBody DodajPacijentaRequest dodajPacijentaRequest) {
        Response response = pacijentService.dodajPacijenta(dodajPacijentaRequest);
        return ResponseEntity.ok(response);
    }


}

