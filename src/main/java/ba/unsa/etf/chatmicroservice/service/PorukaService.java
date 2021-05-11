package ba.unsa.etf.chatmicroservice.service;

import ba.unsa.etf.chatmicroservice.exception.ResourceNotFoundException;
import ba.unsa.etf.chatmicroservice.model.Korisnik;
import ba.unsa.etf.chatmicroservice.model.Poruka;
import ba.unsa.etf.chatmicroservice.dto.PorukaProjection;
import ba.unsa.etf.chatmicroservice.repository.KorisnikRepository;
import ba.unsa.etf.chatmicroservice.repository.PorukaRepository;
import ba.unsa.etf.chatmicroservice.request.DodajPorukuRequest;
import ba.unsa.etf.chatmicroservice.response.PorukaResponse;
import ba.unsa.etf.chatmicroservice.response.PorukePosiljaocaIPrimaocaResponse;
import ba.unsa.etf.chatmicroservice.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class PorukaService {

    private final PorukaRepository porukaRepository;
    private final KorisnikRepository korisnikRepository;

    public PorukaResponse dajPoruku(Long id) {
        Poruka poruka = porukaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji poruka s ovim id-om!"));
        return new PorukaResponse(poruka.getId(), poruka.getSadrzaj(), poruka.getProcitana(), poruka.getDatum(),
                poruka.getVrijeme(), poruka.getPosiljalac().getId(), poruka.getPrimalac().getId());
    }

    public PorukePosiljaocaIPrimaocaResponse dajPorukePosiljaocaIPrimaoca(Long idPosiljaoca, Long idPrimaoca) {
        Korisnik posiljalac = korisnikRepository.findById(idPosiljaoca)
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji posiljalac s ovim id-om!"));
        Korisnik primalac = korisnikRepository.findById(idPrimaoca)
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji primalac s ovim id-om!"));
        List<PorukaProjection> poruke = porukaRepository.findAllByPosiljalacAndPrimalac(posiljalac, primalac);
        return new PorukePosiljaocaIPrimaocaResponse(poruke);
    }

    public Response dodajPoruku(DodajPorukuRequest dodajPorukuRequest) {
        Korisnik posiljalac = korisnikRepository.findById(dodajPorukuRequest.getIdPosiljaoca())
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji posiljalac s ovim id-om!"));
        Korisnik primalac = korisnikRepository.findById(dodajPorukuRequest.getIdPrimaoca())
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji primalac s ovim id-om!"));
        Poruka poruka = new Poruka(
                dodajPorukuRequest.getSadrzaj(),
                0,
                new Date(),
                dodajPorukuRequest.getVrijeme(),
                posiljalac,
                primalac
        );
        porukaRepository.save(poruka);
        return new Response("Uspješno ste dodali poruku!", 200);
    }
}
