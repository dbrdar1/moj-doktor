package ba.unsa.etf.pregledi_i_kartoni.services;

import ba.unsa.etf.pregledi_i_kartoni.exceptions.ResourceNotFoundException;
import ba.unsa.etf.pregledi_i_kartoni.models.PacijentDoktor;
import ba.unsa.etf.pregledi_i_kartoni.models.Pregled;
import ba.unsa.etf.pregledi_i_kartoni.models.Termin;
import ba.unsa.etf.pregledi_i_kartoni.repositories.PacijentDoktorRepository;
import ba.unsa.etf.pregledi_i_kartoni.repositories.TerminRepository;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajTerminRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TerminService {

    private final TerminRepository terminRepository;
    private final PacijentDoktorRepository pacijentDoktorRepository;

    public String spasiTermine(List<Termin> termini) {
        terminRepository.deleteAllInBatch();
        terminRepository.flush();
        terminRepository.saveAll(termini);
        return "Spremljeno!";
    }

    public Termin getTerminById(Long id) {
        String errorMessage = String.format("Ne postoji termin sa id = '%d'", id);
        return terminRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }


    public List<Termin> getAllTermin() {
        return terminRepository
                .findAll();
    }

    public Response dodajTermin(DodajTerminRequest dodajTerminRequest) {
        Optional<PacijentDoktor> pacijentDoktorVeza = pacijentDoktorRepository.findById(dodajTerminRequest.getPacijentDoktorId());
        if(!pacijentDoktorVeza.isPresent()) return new Response("Ne postoji veza pacijent-doktor za dati termin!", 400);
        Termin noviTermin = new Termin(
                dodajTerminRequest.getDatumPregleda(), dodajTerminRequest.getVrijemePregleda(), pacijentDoktorVeza.get()
        );
        //pacijentDoktorVeza.get().getTermini().add(noviTermin);
        terminRepository.save(noviTermin);
        return new Response("Uspješno ste dodali termin pregleda!", 200);
    }

    public Response obrisiTermin(Long id) {
        Termin termin = terminRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji termin s traženim id!"));
        terminRepository.deleteById(id);
        return new Response("Uspješno ste obrisali termin!", 200);
    }

}
