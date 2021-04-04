package ba.unsa.etf.chatmicroservice.services;

import ba.unsa.etf.chatmicroservice.exceptions.ResourceNotFoundException;
import ba.unsa.etf.chatmicroservice.models.Korisnik;
import ba.unsa.etf.chatmicroservice.models.Notifikacija;
import ba.unsa.etf.chatmicroservice.projections.NotifikacijaProjection;
import ba.unsa.etf.chatmicroservice.repositories.KorisnikRepository;
import ba.unsa.etf.chatmicroservice.repositories.NotifikacijaRepository;
import ba.unsa.etf.chatmicroservice.requests.DodajNotifikacijuRequest;
import ba.unsa.etf.chatmicroservice.responses.NotifikacijaResponse;
import ba.unsa.etf.chatmicroservice.responses.NotifikacijeKorisnikaResponse;
import ba.unsa.etf.chatmicroservice.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class NotifikacijaService {

    private final NotifikacijaRepository notifikacijaRepository;
    private final KorisnikRepository korisnikRepository;

    public NotifikacijaResponse dajNotifikaciju(Long id) {
        Notifikacija n = notifikacijaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji notifikacija s ovim id-om!"));
        return new NotifikacijaResponse(n.getId(), n.getNaslov(),n.getTekst(),n.getDatum(),n.getVrijeme(),
                n.getKorisnik().getId());
    }

    public NotifikacijeKorisnikaResponse dajNotifikacijeKorisnika(Long idKorisnika) {
        Korisnik k = korisnikRepository.findById(idKorisnika)
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji korisnik s ovim id-om!"));
        List<NotifikacijaProjection> notifikacije = notifikacijaRepository.findAllByKorisnik(k);
        return new NotifikacijeKorisnikaResponse(notifikacije);
    }

    public Response dodajNotifikaciju(DodajNotifikacijuRequest dodajNotifikacijuRequest) {
        Optional<Korisnik> k = korisnikRepository.findById(dodajNotifikacijuRequest.getIdKorisnika());
        if (!k.isPresent()) return new Response("Id korisnika nije postojeći!", 400);
        Notifikacija n = new Notifikacija(dodajNotifikacijuRequest.getNaslov(), dodajNotifikacijuRequest.getTekst(),
                dodajNotifikacijuRequest.getDatum(),dodajNotifikacijuRequest.getVrijeme(),k.get());
        k.get().getNotifikacije().add(n);
        korisnikRepository.save(k.get());
        return new Response("Uspješno ste dodali notifikaciju!", 200);
    }

    public Response obrisiNotifikaciju(Long id){
        Optional<Notifikacija> notifikacija = notifikacijaRepository.findById(id);
        if (!notifikacija.isPresent()) return new Response("Id notifikacije nije postojeći!", 400);
        notifikacijaRepository.deleteById(id);
        return new Response("Uspješno ste obrisali notifikaciju!", 200);
    }
}
