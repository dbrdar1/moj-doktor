package ba.unsa.etf.termini.services;

import ba.unsa.etf.termini.models.Pacijent;
import ba.unsa.etf.termini.repositories.PacijentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PacijentService {
    private PacijentRepository pacijentRepository;

    public String spasiPacijente(List<Pacijent> korisnici) {
        pacijentRepository.deleteAllInBatch();
        pacijentRepository.flush();
        pacijentRepository.saveAll(korisnici);
        return "Spremljeni pacijenti!";
    }
}
