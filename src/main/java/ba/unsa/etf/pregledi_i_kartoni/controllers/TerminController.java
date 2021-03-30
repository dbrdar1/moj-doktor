package ba.unsa.etf.pregledi_i_kartoni.controllers;

import ba.unsa.etf.pregledi_i_kartoni.models.*;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajTerminRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import ba.unsa.etf.pregledi_i_kartoni.responses.TerminResponse;
import ba.unsa.etf.pregledi_i_kartoni.services.DoktorService;
import ba.unsa.etf.pregledi_i_kartoni.services.PacijentDoktorService;
import ba.unsa.etf.pregledi_i_kartoni.services.PacijentService;
import ba.unsa.etf.pregledi_i_kartoni.services.TerminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class TerminController {

    private final TerminService terminService;

    // private final List<Termin> termini;

    private final Doktor doktor1 = new Doktor("ImeDoktora1", "PrezimeDoktora1", new Date(), "AdresaDoktora1", "061-123-123", "nekimaildoktora1@gmail.com");
    private final Pacijent pacijent1 = new Pacijent( "Ime1", "Prezime1", new Date(), "Adresa1", "061-456-456", "nekimailp1@gmail.com",
            "zenski", 175.2, 68.3, "0+", "/", "/");

    private final Doktor doktor2 = new Doktor("ImeDoktora2", "PrezimeDoktora2", new Date(), "AdresaDoktora2", "061-723-723", "nekimaildoktora2@gmail.com");
    private final Pacijent pacijent2 = new Pacijent( "Ime2", "Prezime2", new Date(), "Adresa2", "061-323-323", "nekimailp2@gmail.com",
            "zenski", 165.4, 56.3, "A-", "/", "/");

    private final PacijentDoktor pd1 = new PacijentDoktor(doktor1, pacijent1);
    private final PacijentDoktor pd2 = new PacijentDoktor(doktor2, pacijent2);



    private final Termin termin1 = new Termin(new Date(), "15:20", pd1);
    private final Termin termin2 = new Termin(new Date(), "16:30", pd2);

    /*

    @GetMapping("/sacuvaj-pocetne-termine")
    public @ResponseBody String spasiListuTermina() {
        termini.add(termin1);
        termini.add(termin2);
        return terminService.spasiTermine(termini);
    }

    */


    // prikaz svih termina
    @GetMapping("/svi-termini")
    public ResponseEntity<List<TerminResponse>> dajSveTermine(){
        List<TerminResponse> trazeniTermini = terminService.dajSveTermine();
        return ResponseEntity.ok(trazeniTermini);
    }

    // prikaz termina na osnovu id
    @GetMapping("/termin/{idTermina}")
        public ResponseEntity<TerminResponse> dajTermin(@PathVariable(value = "idTermina") Long idTermina){
        TerminResponse trazeniTermin = terminService.dajTerminNaOsnovuId(idTermina);
        return ResponseEntity.ok(trazeniTermin);
    }


    // pohrana termina
    @PostMapping("/dodaj-termin")
    public ResponseEntity<Response> dodajTermin(@RequestBody DodajTerminRequest dodajTerminRequest) {
        Response response = terminService.dodajTermin(dodajTerminRequest);
        return ResponseEntity.ok(response);
    }

    // brisanje termina
    @DeleteMapping("/termin/{idTermina}")
    public ResponseEntity<Response> obrisiTermin(@PathVariable(value = "idTermina") Long idTermina) {
        Response response = terminService.obrisiTermin(idTermina);
        return ResponseEntity.ok(response);
    }
}

