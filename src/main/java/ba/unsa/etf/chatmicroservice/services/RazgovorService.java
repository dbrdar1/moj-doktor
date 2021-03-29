package ba.unsa.etf.chatmicroservice.services;

import ba.unsa.etf.chatmicroservice.exceptions.ResourceNotFoundException;
import ba.unsa.etf.chatmicroservice.models.Korisnik;
import ba.unsa.etf.chatmicroservice.models.Notifikacija;
import ba.unsa.etf.chatmicroservice.models.Razgovor;
import ba.unsa.etf.chatmicroservice.repositories.KorisnikRepository;
import ba.unsa.etf.chatmicroservice.repositories.RazgovorRepository;
import ba.unsa.etf.chatmicroservice.requests.DodajNotifikacijuRequest;
import ba.unsa.etf.chatmicroservice.requests.DodajRazgovorRequest;
import ba.unsa.etf.chatmicroservice.responses.NotifikacijaResponse;
import ba.unsa.etf.chatmicroservice.responses.NotifikacijeKorisnikaResponse;
import ba.unsa.etf.chatmicroservice.responses.RazgovoriKorisnikaResponse;
import ba.unsa.etf.chatmicroservice.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RazgovorService {

    private final RazgovorRepository razgovorRepository;
    private final KorisnikRepository korisnikRepository;

    public Razgovor dajRazgovor(Long id) {
        Razgovor razgovor = razgovorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ne postoji razgovor s ovim id-om!"));
        return razgovor;
    }

    public RazgovoriKorisnikaResponse dajRazgovoreKorisnika1(Long idKorisnika) {
        Korisnik k = korisnikRepository.findById(idKorisnika).orElseThrow(() -> new ResourceNotFoundException("Ne postoji korisnik s ovim id-om!"));
        List<Razgovor> razgovori = razgovorRepository.findAllByPrviKorisnik(k);
        return new RazgovoriKorisnikaResponse(razgovori);
    }

    public RazgovoriKorisnikaResponse dajRazgovoreKorisnika2(Long idKorisnika) {
        Korisnik k = korisnikRepository.findById(idKorisnika).orElseThrow(() -> new ResourceNotFoundException("Ne postoji korisnik s ovim id-om!"));
        List<Razgovor> razgovori = razgovorRepository.findAllByDrugiKorisnik(k);
        return new RazgovoriKorisnikaResponse(razgovori);
    }

    public Response dodajRazgovor(DodajRazgovorRequest dodajRazgovorRequest) {
        Optional<Korisnik> prviKorisnik = korisnikRepository.findById(dodajRazgovorRequest.getIdPrvogKorisnika());
        Optional<Korisnik> drugiKorisnik = korisnikRepository.findById(dodajRazgovorRequest.getIdDrugogKorisnika());
        if(!prviKorisnik.isPresent()) return new Response("Id korisnika nije postojeći!", 400);
        if(!drugiKorisnik.isPresent()) return new Response("Id korisnika nije postojeći!", 400);
        Razgovor razgovor = new Razgovor(prviKorisnik.get(), drugiKorisnik.get());
        prviKorisnik.get().getRazgovors1().add(razgovor);
        drugiKorisnik.get().getRazgovors2().add(razgovor);
        korisnikRepository.save(prviKorisnik.get());
        korisnikRepository.save(drugiKorisnik.get());
        return new Response("Uspješno ste dodali razgovor!", 200);
    }
}
