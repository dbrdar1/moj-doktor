package ba.unsa.etf.pregledi_i_kartoni.services;

import ba.unsa.etf.pregledi_i_kartoni.exceptions.ResourceNotFoundException;
import ba.unsa.etf.pregledi_i_kartoni.models.Doktor;
import ba.unsa.etf.pregledi_i_kartoni.models.Pacijent;
import ba.unsa.etf.pregledi_i_kartoni.repositories.DoktorRepository;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajDoktoraRequest;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajPacijentaRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DoktorService {

    private final DoktorRepository doktorRepository;

    public Response dodajDoktora(DodajDoktoraRequest dodajDoktoraRequest) {
        //Optional<Korisnik> k = korisnikRepository.findById(dodajDoktoraRequest.getIdKorisnika());
        //if(!k.isPresent()) return new Response("Id korisnika nije postojeći!", 400);
        Doktor noviDoktor = new Doktor(
                dodajDoktoraRequest.getIme(), dodajDoktoraRequest.getPrezime(), dodajDoktoraRequest.getDatumRodjenja(),
                dodajDoktoraRequest.getAdresa(), dodajDoktoraRequest.getBrojTelefona(), dodajDoktoraRequest.getEmail()
        );
        //k.get().getNotifikacije().add(n);
        doktorRepository.save(noviDoktor);
        return new Response("Uspješno ste dodali doktora!", 200);
    }


    public Doktor dajDoktoraNaOsnovuId(Long id) {
        String errorMessage = String.format("Ne postoji doktor sa id = '%d'", id);
        return doktorRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }

    public List<Doktor> dajSveDoktore() {
        return doktorRepository
                .findAll();
    }
}
