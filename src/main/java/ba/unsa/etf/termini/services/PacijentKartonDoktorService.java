package ba.unsa.etf.termini.services;

import ba.unsa.etf.termini.models.PacijentKartonDoktor;
import ba.unsa.etf.termini.repositories.DoktorRepository;
import ba.unsa.etf.termini.repositories.PacijentKartonDoktorRepository;
import ba.unsa.etf.termini.repositories.PacijentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PacijentKartonDoktorService {
    private DoktorRepository doktorRepository;
    private PacijentRepository pacijentRepository;

    private PacijentKartonDoktorRepository pacijentKartonDoktorRepository;

    public String spasiVezuDoktorPacijent(PacijentKartonDoktor pkd) {
        pacijentKartonDoktorRepository.deleteAllInBatch();
        pacijentKartonDoktorRepository.flush();
        pacijentRepository.save(pkd.getPacijent());
        doktorRepository.save(pkd.getDoktor());
        pacijentKartonDoktorRepository.save(pkd);
        return "Spremljena veza doktor pacijent!";
    }
}
