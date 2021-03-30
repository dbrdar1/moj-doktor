package ba.unsa.etf.chatmicroservice.services;

import ba.unsa.etf.chatmicroservice.exceptions.ResourceNotFoundException;
import ba.unsa.etf.chatmicroservice.models.Korisnik;
import ba.unsa.etf.chatmicroservice.models.Poruka;
import ba.unsa.etf.chatmicroservice.repositories.KorisnikRepository;
import ba.unsa.etf.chatmicroservice.repositories.PorukaRepository;
import ba.unsa.etf.chatmicroservice.requests.DodajPorukuRequest;
import ba.unsa.etf.chatmicroservice.responses.PorukePosiljaocaIPrimaocaResponse;
import ba.unsa.etf.chatmicroservice.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PorukaService {

    private final PorukaRepository porukaRepository;
    private final KorisnikRepository korisnikRepository;

    public Poruka dajPoruku(Long id) {
        Poruka poruka = porukaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji poruka s ovim id-om!"));
        return poruka;
    }

    public PorukePosiljaocaIPrimaocaResponse dajPorukePosiljaocaIPrimaoca(Long idPosiljaoca, Long idPrimaoca) {
        Korisnik posiljalac = korisnikRepository.findById(idPosiljaoca).orElseThrow(() -> new ResourceNotFoundException("Ne postoji korisnik s ovim id-om!"));
        Korisnik primalac = korisnikRepository.findById(idPrimaoca).orElseThrow(() -> new ResourceNotFoundException("Ne postoji korisnik s ovim id-om!"));
        List<Poruka> poruke = porukaRepository.findAllByPosiljalacAndPrimalac(posiljalac, primalac);
        return new PorukePosiljaocaIPrimaocaResponse(poruke);
    }

    public Response dodajPoruku(DodajPorukuRequest dodajPorukuRequest) {
        Optional<Korisnik> posiljalac = korisnikRepository.findById(dodajPorukuRequest.getIdPosiljaoca());
        Optional<Korisnik> primalac = korisnikRepository.findById(dodajPorukuRequest.getIdPrimaoca());
        if(!posiljalac.isPresent()) return new Response("Id korisnika nije postojeći!", 400);
        if(!primalac.isPresent()) return new Response("Id korisnika nije postojeći!", 400);
        Poruka poruka = new Poruka(dodajPorukuRequest.getSadrzaj(), 0, new Date(), dodajPorukuRequest.getVrijeme(), posiljalac.get(), primalac.get());
        porukaRepository.save(poruka);
        return new Response("Uspješno ste dodali poruku!", 200);
    }
}
