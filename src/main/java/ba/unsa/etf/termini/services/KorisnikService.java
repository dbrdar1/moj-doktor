package ba.unsa.etf.termini.services;

import ba.unsa.etf.termini.Requests.DodajKorisnikaRequest;
import ba.unsa.etf.termini.Requests.DodajNotifikacijuRequest;
import ba.unsa.etf.termini.Responses.Response;
import ba.unsa.etf.termini.models.Korisnik;
import ba.unsa.etf.termini.models.Notifikacija;
import ba.unsa.etf.termini.repositories.KorisnikRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;

    public String spasiKorisnike(List<Korisnik> korisnici) {
        korisnikRepository.deleteAllInBatch();
        korisnikRepository.flush();
        korisnikRepository.saveAll(korisnici);
        return "Spremljeno!";
    }

    public Response dodajKorisnika(DodajKorisnikaRequest dodajKorisnikaRequest) {
        Korisnik k = new Korisnik(dodajKorisnikaRequest.getIme(),dodajKorisnikaRequest.getPrezime(),dodajKorisnikaRequest.getDatumRodjenja(),
                dodajKorisnikaRequest.getAdresa(),dodajKorisnikaRequest.getBrojTelefona(),dodajKorisnikaRequest.getEmail());
        korisnikRepository.save(k);
        return new Response("Uspješno ste dodali korisnika!", 200);
    }
}
