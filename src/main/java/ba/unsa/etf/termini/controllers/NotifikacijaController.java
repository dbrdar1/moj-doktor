package ba.unsa.etf.termini.controllers;

import ba.unsa.etf.termini.Requests.DodajNotifikacijuRequest;
import ba.unsa.etf.termini.Responses.NotifikacijeKorisnikaResponse;
import ba.unsa.etf.termini.Responses.Response;
import ba.unsa.etf.termini.models.Korisnik;
import ba.unsa.etf.termini.models.Notifikacija;
import ba.unsa.etf.termini.models.Pacijent;
import ba.unsa.etf.termini.repositories.NotifikacijaRepository;
import ba.unsa.etf.termini.repositories.PacijentRepository;
import ba.unsa.etf.termini.services.NotifikacijaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@AllArgsConstructor
@RestController
public class NotifikacijaController {
    private final NotifikacijaService notifikacijaService;
    private PacijentRepository pacijentRepository;
    private NotifikacijaRepository notifikacijaRepository;
    private final Pacijent k1 = new Pacijent(
            "Ana",
            "Anic",
            new Date(),
            "NekaAdresa",
            "061456321","ana@mail.com");
    private final Pacijent k2 = new Pacijent(
            "Nina",
            "Ninic",
            new Date(),
            "NekaAdresa",
            "061456321","nina@mail.com");

    private final List<Notifikacija> notifikacije;
    private final Date datum = new Date(2021,3,17);
    private final String vrijeme = "9:00";

    @GetMapping("/pohraniPocetneNotifikacije")
    public @ResponseBody
    String spasiListuNotifikacija(){
        Korisnik a= pacijentRepository.save(k1);
        Korisnik b= pacijentRepository.save(k2);
        notifikacije.add(new Notifikacija(
                "Otkazan pregled!",
                "Otkazan pregled 21.3.2020.",
                datum,
                vrijeme,
                b
        ));
        notifikacije.add(new Notifikacija(
                "Otkazan pregled!",
                "Otkazan pregled 20.3.2020.",
                datum,
                vrijeme,
                a
        ));
        return  notifikacijaService.spasiNotifikacije(notifikacije);
    }

    @DeleteMapping("/notifikacije/{id}")
    public ResponseEntity<Response> obrisiNotifikaciju(@PathVariable Long id) {
        Response response = notifikacijaService.obrisiNotifikaciju(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dodaj-notifikaciju")
    public ResponseEntity<Response> dodajNotifikaciju(@RequestBody DodajNotifikacijuRequest dodajNotifikacijuRequest){
        Response response = notifikacijaService.dodajNotifikaciju(dodajNotifikacijuRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/notifikacije-korisnika/{idKorisnika}")
    public ResponseEntity<NotifikacijeKorisnikaResponse> dajNotifikacijeKorisnika(@PathVariable Long idKorisnika){
        NotifikacijeKorisnikaResponse response = notifikacijaService.dajNotifikacijeKorisnika(idKorisnika);
        return ResponseEntity.ok(response);
    }
}
