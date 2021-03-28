package ba.unsa.etf.pregledi_i_kartoni.services;

import ba.unsa.etf.pregledi_i_kartoni.exceptions.ResourceNotFoundException;
import ba.unsa.etf.pregledi_i_kartoni.models.Pacijent;
import ba.unsa.etf.pregledi_i_kartoni.repositories.PacijentRepository;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajPacijentaRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class PacijentService {

    private final PacijentRepository pacijentRepository;


    public Response dodajPacijenta(DodajPacijentaRequest dodajPacijentaRequest) {
        //Optional<Korisnik> k = korisnikRepository.findById(dodajPacijentaRequest.getIdKorisnika());
        //if(!k.isPresent()) return new Response("Id korisnika nije postojeći!", 400);
        Pacijent noviPacijent = new Pacijent(
                dodajPacijentaRequest.getIme(), dodajPacijentaRequest.getPrezime(), dodajPacijentaRequest.getDatumRodjenja(),
                dodajPacijentaRequest.getAdresa(), dodajPacijentaRequest.getBrojTelefona(), dodajPacijentaRequest.getEmail(),
                dodajPacijentaRequest.getSpol(), dodajPacijentaRequest.getVisina(), dodajPacijentaRequest.getTezina(),
                dodajPacijentaRequest.getKrvnaGrupa(), dodajPacijentaRequest.getHronicneBolesti(), dodajPacijentaRequest.getHronicnaTerapija()
        );
        //k.get().getNotifikacije().add(n);
        pacijentRepository.save(noviPacijent);
        return new Response("Uspješno ste dodali pacijenta!", 200);
    }


    public Pacijent getPacijentById(Long id) {
        String errorMessage = String.format("Ne postoji pacijent sa id = '%d'", id);
        return pacijentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }

    public List<Pacijent> getAllPacijent() {
        return pacijentRepository
                .findAll();
    }

}
