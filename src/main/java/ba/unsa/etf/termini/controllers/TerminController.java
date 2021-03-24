package ba.unsa.etf.termini.controllers;

import ba.unsa.etf.termini.Requests.DodajTerminRequest;
import ba.unsa.etf.termini.Responses.Response;
import ba.unsa.etf.termini.models.*;
import ba.unsa.etf.termini.repositories.DoktorRepository;
import ba.unsa.etf.termini.repositories.PacijentKartonDoktorRepository;
import ba.unsa.etf.termini.repositories.PacijentRepository;
import ba.unsa.etf.termini.repositories.TerminRepository;
import ba.unsa.etf.termini.services.TerminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
@AllArgsConstructor
@RestController
public class TerminController {
    private TerminService terminService;
    private PacijentRepository pacijentRepository;
    private DoktorRepository doktorRepository;
    private PacijentKartonDoktorRepository pacijentKartonDoktorRepository;
    private TerminRepository terminRepository;

    private final Pacijent p1 = new Pacijent(
            "Samra",
            "Pusina",
            new Date(),
            "NekaAdresa",
            "061456321","samra@mail");
    private final Pacijent p2 = new Pacijent(
            "Esmina",
            "Radusic",
            new Date(),
            "NekaAdresa",
            "061456321",
            "esmina@mail");
    private final Doktor d1 = new Doktor(
            "Dzavid",
            "Brdar",
            new Date(),
            "NekaAdresa",
            "061456321",
            "dzavid@mail.com",
            "Doktor opce prakse");
    private final Doktor d2 = new Doktor(
            "Jusuf",
            "Delalic",
            new Date(),
            "NekaAdresa",
            "061456321",
            "jusuf@mail",
            "Doktor opce prakse");

    @GetMapping("/pohraniPocetneTermine")
    public
    String spasiListuTermina() {
        pacijentKartonDoktorRepository.deleteAllInBatch();
        pacijentKartonDoktorRepository.flush();
        terminRepository.deleteAllInBatch();
        terminRepository.flush();
        System.out.println(d1);
        PacijentKartonDoktor pkd1=new PacijentKartonDoktor(d1,p1);
        PacijentKartonDoktor pkd2=new PacijentKartonDoktor(d2,p1);
        PacijentKartonDoktor pkd3=new PacijentKartonDoktor(d1,p2);
        PacijentKartonDoktor pkd4=new PacijentKartonDoktor(d2,p2);

        p1.getVezeSaDoktorima().add(pkd1);
        p1.getVezeSaDoktorima().add(pkd2);
        p2.getVezeSaDoktorima().add(pkd3);
        p2.getVezeSaDoktorima().add(pkd4);
        d1.getVezeSaPacijentima().add(pkd1);
        d2.getVezeSaPacijentima().add(pkd2);
        d1.getVezeSaPacijentima().add(pkd3);
        d2.getVezeSaPacijentima().add(pkd4);

        Termin termin1= new Termin(
                new Date(2021,5,25),
                "10:00",
                pkd1 );
        Termin termin2= new Termin(
                new Date(2021,5,25),
                "10:00",
                pkd2);
        Termin termin3= new Termin(
                new Date(2021,5,26),
                "11:00",
                pkd3);
        Termin termin4= new Termin(
                new Date(2021,5,26),
                "9:00",
                pkd4);
        pkd1.getTermini().add(termin1);
        pkd2.getTermini().add(termin2);
        pkd3.getTermini().add(termin3);
        pkd4.getTermini().add(termin4);

        pacijentRepository.save(p1); pacijentRepository.save(p2);
        doktorRepository.save(d1); doktorRepository.save(d2);

        return "Spaseni pocetni termini!";
    }

    @PostMapping("/dodaj-termin")
    public ResponseEntity<Response> dodajTermin(@RequestBody DodajTerminRequest dodajTerminRequest){
        Response response = terminService.dodajTermin(dodajTerminRequest);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/termini/{id}")
    public ResponseEntity<Response> obrisiTermin(@PathVariable Long id) {
        Response response = terminService.obrisiTermin(id);
        return ResponseEntity.ok(response);
    }
}
