package ba.unsa.etf.pregledi_i_kartoni.services;

import ba.unsa.etf.pregledi_i_kartoni.exceptions.ResourceNotFoundException;
import ba.unsa.etf.pregledi_i_kartoni.models.Korisnik;
import ba.unsa.etf.pregledi_i_kartoni.models.Korisnik;
import ba.unsa.etf.pregledi_i_kartoni.models.Korisnik;
import ba.unsa.etf.pregledi_i_kartoni.repositories.KorisnikRepository;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajKorisnikaRequest;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajKorisnikaRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.KorisnikResponse;
import ba.unsa.etf.pregledi_i_kartoni.responses.KorisnikResponse;
import ba.unsa.etf.pregledi_i_kartoni.responses.KorisnikResponse;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;

    public Response dodajKorisnika(DodajKorisnikaRequest dodajKorisnikaRequest) {
        Korisnik noviKorisnik = new Korisnik(
                dodajKorisnikaRequest.getIme(), dodajKorisnikaRequest.getPrezime(), dodajKorisnikaRequest.getDatumRodjenja(),
                dodajKorisnikaRequest.getAdresa(), dodajKorisnikaRequest.getBrojTelefona(), dodajKorisnikaRequest.getEmail()
        );
        korisnikRepository.save(noviKorisnik);
        return new Response("Uspješno ste dodali korisnika!", 200);
    }


    public KorisnikResponse dajKorisnikaNaOsnovuId(Long idKorisnika) {
        String errorMessageKorisnik = String.format("Ne postoji korisnik sa id = '%d'!", idKorisnika);
        Korisnik trazeniKorisnik = korisnikRepository.findById(idKorisnika).orElseThrow(() -> new ResourceNotFoundException(errorMessageKorisnik));
        return new KorisnikResponse(trazeniKorisnik.getId(), trazeniKorisnik.getIme(), trazeniKorisnik.getPrezime(),
                trazeniKorisnik.getDatumRodjenja(), trazeniKorisnik.getAdresa(), trazeniKorisnik.getBrojTelefona(),
                trazeniKorisnik.getEmail()
        );

    }

    public List<KorisnikResponse> dajSveKorisnike() {
        List<Korisnik> korisnici = korisnikRepository.findAll();
        List<KorisnikResponse> listaKorisnikResponse = new ArrayList<>();
        for (Korisnik korisnik : korisnici) {
            listaKorisnikResponse.add(new KorisnikResponse(korisnik.getId(), korisnik.getIme(), korisnik.getPrezime(),
                            korisnik.getDatumRodjenja(), korisnik.getAdresa(), korisnik.getBrojTelefona(),
                            korisnik.getEmail()
                    )
            );
        }

        return listaKorisnikResponse;
    }
}
