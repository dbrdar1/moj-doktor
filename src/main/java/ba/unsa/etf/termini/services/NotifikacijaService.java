package ba.unsa.etf.termini.services;

import ba.unsa.etf.termini.Requests.DodajNotifikacijuRequest;
import ba.unsa.etf.termini.Responses.NotifikacijeKorisnikaResponse;
import ba.unsa.etf.termini.Responses.Response;
import ba.unsa.etf.termini.exceptions.ResourceNotFoundException;
import ba.unsa.etf.termini.models.Korisnik;
import ba.unsa.etf.termini.models.Notifikacija;
import ba.unsa.etf.termini.repositories.KorisnikRepository;
import ba.unsa.etf.termini.repositories.NotifikacijaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class NotifikacijaService {

    private final NotifikacijaRepository notifikacijaRepository;
    private KorisnikRepository korisnikRepository;

    public String spasiNotifikacije(List<Notifikacija> notifikacije) {
        notifikacijaRepository.deleteAllInBatch();
        notifikacijaRepository.flush();
        notifikacijaRepository.saveAll(notifikacije);
        return "Spremljene notifikacije!";
    }

    public Response dodajNotifikaciju(DodajNotifikacijuRequest dodajNotifikacijuRequest) {
        Optional<Korisnik> k = korisnikRepository.findById(dodajNotifikacijuRequest.getIdKorisnika());
        if(!k.isPresent()) return new Response("Id korisnika nije postojeći!", 400);
        Notifikacija n = new Notifikacija(dodajNotifikacijuRequest.getNaslov(), dodajNotifikacijuRequest.getTekst(),
                dodajNotifikacijuRequest.getDatum(),dodajNotifikacijuRequest.getVrijeme(),k.get());
        k.get().getNotifikacije().add(n);
        korisnikRepository.save(k.get());
        return new Response("Uspješno ste dodali notifikaciju!", 200);
    }

    public NotifikacijeKorisnikaResponse dajNotifikacijeKorisnika (Long idKorisnika){
        Korisnik k = korisnikRepository.findById(idKorisnika).orElseThrow(() -> new ba.unsa.etf.termini.exceptions.ResourceNotFoundException("Ne postoji korisnik s ovim id-om!"));
        List<Notifikacija> notifikacije = notifikacijaRepository.findAllByKorisnik(k);
        return new NotifikacijeKorisnikaResponse(notifikacije);
    }

    public Response obrisiNotifikaciju(Long id){
        Notifikacija d = notifikacijaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ne postoji notifikacija s ovim id-om!"));;
        notifikacijaRepository.deleteById(id);
        return new Response("Uspješno ste obrisali notifikaciju!", 200);
    }

}