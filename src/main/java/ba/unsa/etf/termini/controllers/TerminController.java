package ba.unsa.etf.termini.controllers;

import ba.unsa.etf.termini.Requests.DodajNotifikacijuRequest;
import ba.unsa.etf.termini.Requests.DodajTerminRequest;
import ba.unsa.etf.termini.Responses.Response;
import ba.unsa.etf.termini.models.*;
import ba.unsa.etf.termini.repositories.PacijentKartonDoktorRepository;
import ba.unsa.etf.termini.services.PacijentKartonDoktorService;
import ba.unsa.etf.termini.services.TerminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@RestController
public class TerminController {
    private TerminService terminService;
    private PacijentKartonDoktorRepository pacijentKartonDoktorRepository;

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
    public @ResponseBody
    String spasiListuTermina() {
        List<Termin> termini = new LinkedList<>();
        PacijentKartonDoktor pkd1=new PacijentKartonDoktor(d1,p1);
        PacijentKartonDoktor pkd2=new PacijentKartonDoktor(d2,p1);
        PacijentKartonDoktor pkd3=new PacijentKartonDoktor(d1,p2);
        PacijentKartonDoktor pkd4=new PacijentKartonDoktor(d2,p2);
        pkd1=pacijentKartonDoktorRepository.save(pkd1);
        pkd2=pacijentKartonDoktorRepository.save(pkd2);
        pkd3=pacijentKartonDoktorRepository.save(pkd3);
        pkd4=pacijentKartonDoktorRepository.save(pkd4);

        termini.add(new Termin(
                new Date(2021,5,25),
                "10:00",
                pkd1
        ));
        termini.add(new Termin(
                new Date(2021,5,25),
                "10:00",
                pkd2
        ));
        termini.add(new Termin(
                new Date(2021,5,26),
                "11:00",
                pkd3
        ));
        termini.add(new Termin(
                new Date(2021,5,26),
                "9:00",
                pkd4
        ));
        return terminService.pohraniPocetneTermine(termini);
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
