package ba.unsa.etf.pregledi_i_kartoni.controllers;

import ba.unsa.etf.pregledi_i_kartoni.models.*;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import ba.unsa.etf.pregledi_i_kartoni.services.PregledService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class PregledController {

    private final PregledService pregledService;


    @GetMapping("/inicijalizirajBazu")
    public ResponseEntity<Response> inicijalizacija() {

        String poruka = pregledService.incijalizirajBazu();
        return ResponseEntity.ok(new Response(poruka));

    }


    @GetMapping("/dajSvePreglede")
    public ResponseEntity<List<Pregled>> getSviPregledi(){
        List<Pregled> trazeniPregledi = pregledService.getAllPregled();
        return ResponseEntity.ok(trazeniPregledi);
    }

    @GetMapping("/dajPregled")
    public ResponseEntity<Pregled> getPregled (@RequestParam(value = "id") Long idPregleda){
        Pregled trazeniPregled = pregledService.getPregledById(idPregleda);
        return ResponseEntity.ok(trazeniPregled);
    }

    @GetMapping("/dajPregled/{idDoktor}/{idPacijent}")
    public ResponseEntity<List<Pregled>> getPregledDoktorPacijent (@PathVariable(value = "idDoktor") Long idDoktor, @PathVariable(value = "idPacijent") Long idPacijent) {
        List<Pregled> trazeniPregledi = pregledService.getPregledByIdDoktoraIPacijenta(idDoktor, idPacijent);
        return ResponseEntity.ok(trazeniPregledi);
    }


}

