package ba.unsa.etf.pregledi_i_kartoni.services;

import ba.unsa.etf.pregledi_i_kartoni.exceptions.ResourceNotFoundException;
import ba.unsa.etf.pregledi_i_kartoni.models.Doktor;
import ba.unsa.etf.pregledi_i_kartoni.models.Korisnik;
import ba.unsa.etf.pregledi_i_kartoni.repositories.KorisnikRepository;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajDoktoraRequest;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajKorisnikaRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;

    public Response dodajKorisnika(DodajKorisnikaRequest dodajKorisnikaRequest) {
        //Optional<Korisnik> k = korisnikRepository.findById(dodajKorisnikaRequest.getIdKorisnika());
        //if(!k.isPresent()) return new Response("Id korisnika nije postojeći!", 400);
        Korisnik noviKorisnik = new Korisnik(
                dodajKorisnikaRequest.getIme(), dodajKorisnikaRequest.getPrezime(), dodajKorisnikaRequest.getDatumRodjenja(),
                dodajKorisnikaRequest.getAdresa(), dodajKorisnikaRequest.getBrojTelefona(), dodajKorisnikaRequest.getEmail()
        );
        //k.get().getNotifikacije().add(n);
        korisnikRepository.save(noviKorisnik);
        return new Response("Uspješno ste dodali korisnika!", 200);
    }


    public Korisnik dajKorisnikaNaOsnovuId(Long id) {
        String errorMessage = String.format("Ne postoji korisnik sa id = '%d'", id);
        return korisnikRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }

    public List<Korisnik> dajSveKorisnike() {
        return korisnikRepository
                .findAll();
    }
}
