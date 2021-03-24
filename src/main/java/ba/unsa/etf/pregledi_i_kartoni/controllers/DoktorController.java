package ba.unsa.etf.pregledi_i_kartoni.controllers;

import ba.unsa.etf.pregledi_i_kartoni.models.KartonPacijent;
import ba.unsa.etf.pregledi_i_kartoni.models.Korisnik;
import ba.unsa.etf.pregledi_i_kartoni.models.Doktor;
import ba.unsa.etf.pregledi_i_kartoni.models.PacijentKartonDoktor;
import ba.unsa.etf.pregledi_i_kartoni.services.DoktorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class DoktorController {


    private final DoktorService doktorService;


    private final List<Doktor> doktori;



    private final Doktor doktor1 = new Doktor();
    private final Doktor doktor2 = new Doktor();



    @PostMapping("/pohraniDoktore")
    public @ResponseBody String spasiListuDoktora() {
        doktori.add(doktor1);
        doktori.add(doktor2);
        return doktorService.spasiDoktore(doktori);
    }


    @GetMapping("/dajDoktora")
    public ResponseEntity<Doktor> getDoktor (@RequestParam(value = "id") Long idDoktora){
        Doktor trazeniDoktor = doktorService.getDoktorById(idDoktora);
        return ResponseEntity.ok(trazeniDoktor);
    }

    @GetMapping("/dajSveDoktore")
    public ResponseEntity<List<Doktor>> getSviDoktori (){
        List<Doktor> trazeniDoktori = doktorService.getAllDoktor();
        return ResponseEntity.ok(trazeniDoktori);
    }
}

