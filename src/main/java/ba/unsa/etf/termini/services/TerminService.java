package ba.unsa.etf.termini.services;

import ba.unsa.etf.termini.models.Termin;
import ba.unsa.etf.termini.repositories.TerminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TerminService {
    private TerminRepository terminRepository;

    public String pohraniPocetneTermine(List<Termin> termini) {
        terminRepository.deleteAllInBatch();
        terminRepository.flush();
        terminRepository.saveAll(termini);
        return "Spremljeni pocetni termini!";
    }
}
