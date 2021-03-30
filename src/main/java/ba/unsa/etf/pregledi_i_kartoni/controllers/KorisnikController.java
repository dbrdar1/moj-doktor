package ba.unsa.etf.pregledi_i_kartoni.controllers;

import ba.unsa.etf.pregledi_i_kartoni.models.Doktor;
import ba.unsa.etf.pregledi_i_kartoni.models.Korisnik;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajDoktoraRequest;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajKorisnikaRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.KorisnikResponse;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import ba.unsa.etf.pregledi_i_kartoni.services.DoktorService;
import ba.unsa.etf.pregledi_i_kartoni.services.KorisnikService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class KorisnikController {

    private final KorisnikService korisnikService;

    // prikaz jednog korisnika na osnovu id
    @GetMapping("/korisnik/{idKorisnika}")
    public ResponseEntity<KorisnikResponse> dajKorisnika(@PathVariable(value = "idKorisnika") Long idKorisnika){
        KorisnikResponse trazeniKorisnik = korisnikService.dajKorisnikaNaOsnovuId(idKorisnika);
        return ResponseEntity.ok(trazeniKorisnik);
    }

    // prikaz svih korisnika
    @GetMapping("/svi-korisnici")
    public ResponseEntity<List<KorisnikResponse>> dajSveKorisnike(){
        List<KorisnikResponse> sviKorisnici = korisnikService.dajSveKorisnike();
        return ResponseEntity.ok(sviKorisnici);
    }

    // pohrana korisnika
    @PostMapping("/dodaj-korisnika")
    public ResponseEntity<Response> dodajKorisnika(@RequestBody DodajKorisnikaRequest dodajKorisnikaRequest) {
        Response response = korisnikService.dodajKorisnika(dodajKorisnikaRequest);
        return ResponseEntity.ok(response);
    }
}

