package ba.unsa.etf.termini.services;

import ba.unsa.etf.termini.models.Doktor;
import ba.unsa.etf.termini.repositories.DoktorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DoktorService {
    private DoktorRepository doktorRepository;

    public String spasiDoktore(List<Doktor> korisnici) {
        doktorRepository.deleteAllInBatch();
        doktorRepository.flush();
        doktorRepository.saveAll(korisnici);
        return "Spremljeni doktori!";
    }
}
