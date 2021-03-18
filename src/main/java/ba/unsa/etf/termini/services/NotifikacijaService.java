package ba.unsa.etf.termini.services;

import ba.unsa.etf.termini.models.Notifikacija;
import ba.unsa.etf.termini.repositories.NotifikacijaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class NotifikacijaService {

    private final NotifikacijaRepository notifikacijaRepository;

    public String spasiNotifikacije(List<Notifikacija> notifikacije) {
        notifikacijaRepository.deleteAllInBatch();
        notifikacijaRepository.flush();
        notifikacijaRepository.saveAll(notifikacije);
        return "Spremljene notifikacije!";
    }
}