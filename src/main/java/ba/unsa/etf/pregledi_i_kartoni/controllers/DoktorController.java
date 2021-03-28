package ba.unsa.etf.pregledi_i_kartoni.controllers;

import ba.unsa.etf.pregledi_i_kartoni.models.Doktor;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajDoktoraRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import ba.unsa.etf.pregledi_i_kartoni.services.DoktorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class DoktorController {


    private final DoktorService doktorService;


    // prikaz jednog doktora na osnovu id
    @GetMapping("/doktor")
    public ResponseEntity<Doktor> dajDoktora(@RequestParam(value = "id") Long idDoktora){
        Doktor trazeniDoktor = doktorService.dajDoktoraNaOsnovuId(idDoktora);
        return ResponseEntity.ok(trazeniDoktor);
    }

    // prikaz svih doktora
    @GetMapping("/svi-doktori")
    public ResponseEntity<List<Doktor>> dajSveDoktore(){
        List<Doktor> trazeniDoktori = doktorService.dajSveDoktore();
        return ResponseEntity.ok(trazeniDoktori);
    }

    // pohrana doktora
    @PostMapping("/dodaj-doktora")
    public ResponseEntity<Response> dodajDoktora(@RequestBody DodajDoktoraRequest dodajDoktoraRequest) {
        Response response = doktorService.dodajDoktora(dodajDoktoraRequest);
        return ResponseEntity.ok(response);
    }

}

