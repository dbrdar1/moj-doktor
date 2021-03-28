package ba.unsa.etf.pregledi_i_kartoni.controllers;

import ba.unsa.etf.pregledi_i_kartoni.models.*;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajPacijentaRequest;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajPregledRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import ba.unsa.etf.pregledi_i_kartoni.services.PregledService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class PregledController {

    private final PregledService pregledService;


    @GetMapping("/inicijaliziraj-bazu")
    public ResponseEntity<Response> inicijalizacija() {

        String poruka = pregledService.incijalizirajBazu();
        return ResponseEntity.ok(new Response(poruka));

    }


    @GetMapping("/svi-pregledi")
    public ResponseEntity<List<Pregled>> getSviPregledi(){
        List<Pregled> trazeniPregledi = pregledService.dajSvePreglede();
        return ResponseEntity.ok(trazeniPregledi);
    }

    // Prikaz pregleda na osnovu id pregleda
    @GetMapping("/pregled")
    public ResponseEntity<Pregled> dajPregled(@RequestParam(value = "id") Long idPregleda){
        Pregled trazeniPregled = pregledService.dajPregledNaOsonvuId(idPregleda);
        return ResponseEntity.ok(trazeniPregled);
    }

    // Prikaz pregleda na osnovu id doktora i id pacijenta
    @GetMapping("/pregled/{idDoktor}/{idPacijent}")
    public ResponseEntity<List<Pregled>> dajPregledDoktorPacijent(@PathVariable(value = "idDoktor") Long idDoktor, @PathVariable(value = "idPacijent") Long idPacijent) {
        List<Pregled> trazeniPregledi = pregledService.dajPregledeNaOsnovuIdDoktoraIdPacijenta(idDoktor, idPacijent);
        return ResponseEntity.ok(trazeniPregledi);
    }

    // pohrana pregleda
    @PostMapping("/dodaj-pregled")
    public ResponseEntity<Response> dodajPregled(@RequestBody DodajPregledRequest dodajPregledRequest) {
        Response response = pregledService.dodajPregled(dodajPregledRequest);
        return ResponseEntity.ok(response);
    }

}

