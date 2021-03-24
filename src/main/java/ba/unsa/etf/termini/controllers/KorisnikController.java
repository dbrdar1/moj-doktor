package ba.unsa.etf.termini.controllers;

import ba.unsa.etf.termini.Requests.DodajKorisnikaRequest;
import ba.unsa.etf.termini.Responses.Response;
import ba.unsa.etf.termini.models.Korisnik;
import ba.unsa.etf.termini.repositories.KorisnikRepository;
import ba.unsa.etf.termini.services.KorisnikService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class KorisnikController {

    private KorisnikRepository korisnikRepository;
    private final KorisnikService korisnikService;
    private final List<Korisnik> korisnici;
    private final Korisnik k1 = new Korisnik(
            "Samra",
            "Pusina",
            new Date(),
            "NekaAdresa","061456321",
            "samra@mail.com");

    private final Korisnik k2 = new Korisnik(
            "Esmina",
            "Radusic",
            new Date(),
            "NekaAdresa","061456321",
            "esmina@mail.com");

   /* @GetMapping("/pohraniKorisnike")
    public @ResponseBody String spasiListuKorisnika(){
        korisnici.add(k1);
        korisnici.add(k2);
        return  korisnikService.spasiKorisnike(korisnici);
    }*/

    @GetMapping("/korisnici")
    List<Korisnik> all() {
        return korisnikRepository.findAll();
    }

    @GetMapping("/korisnici/{id}")
    Korisnik one(@PathVariable Long id) throws Exception {

        return korisnikRepository.findById(id)
                .orElseThrow(() -> new Exception("Korisnik nije pronađen"));
    }

    @PostMapping("/korisnici")
    public ResponseEntity<Response> dodajKorisnika(@RequestBody DodajKorisnikaRequest dodajKorisnikaRequest){
        Response response = korisnikService.dodajKorisnika(dodajKorisnikaRequest);
        return ResponseEntity.ok(response);
    }
}

