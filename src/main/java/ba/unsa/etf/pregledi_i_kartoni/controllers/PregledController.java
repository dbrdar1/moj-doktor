package ba.unsa.etf.pregledi_i_kartoni.controllers;

import ba.unsa.etf.pregledi_i_kartoni.models.*;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajPacijentaRequest;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajPregledRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.PregledResponse;
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
    public ResponseEntity<List<PregledResponse>> getSviPregledi(){
        List<PregledResponse> trazeniPregledi = pregledService.dajSvePreglede();
        return ResponseEntity.ok(trazeniPregledi);
    }

    // Prikaz pregleda na osnovu id pregleda
    @GetMapping("/pregled/{idPregleda}")
    public ResponseEntity<PregledResponse> dajPregled(@PathVariable(value = "idPregleda") Long idPregleda){
        PregledResponse trazeniPregled = pregledService.dajPregledNaOsonvuId(idPregleda);
        return ResponseEntity.ok(trazeniPregled);
    }

    // filtriranje pregleda (na osnovu id doktora, id pacijenta, id termina)
    @GetMapping("/pregled")
    public ResponseEntity<List<PregledResponse>> filtrirajPreglede(@RequestParam(value = "idDoktor", required = false) Long idDoktor, @RequestParam(value = "idPacijent", required = false) Long idPacijent, @RequestParam(value = "idTermin", required = false) Long idTermin) {
        List<PregledResponse> trazeniPregledi = pregledService.filtrirajPreglede(idDoktor, idPacijent, idTermin);
        return ResponseEntity.ok(trazeniPregledi);
    }

    // pohrana pregleda
    @PostMapping("/dodaj-pregled")
    public ResponseEntity<Response> dodajPregled(@RequestBody DodajPregledRequest dodajPregledRequest) {
        Response response = pregledService.dodajPregled(dodajPregledRequest);
        return ResponseEntity.ok(response);
    }

    // brisanje pregleda
    @DeleteMapping("/pregled/{idPregleda}")
    public ResponseEntity<Response> obrisiPregled(@PathVariable(value = "idPregleda") Long idPregleda) {
        Response response = pregledService.obrisiPregled(idPregleda);
        return ResponseEntity.ok(response);
    }

}

