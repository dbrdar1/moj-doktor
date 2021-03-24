package ba.unsa.etf.pregledi_i_kartoni.services;

import ba.unsa.etf.pregledi_i_kartoni.models.Korisnik;
import ba.unsa.etf.pregledi_i_kartoni.models.PacijentKartonDoktor;
import ba.unsa.etf.pregledi_i_kartoni.repositories.KorisnikRepository;
import ba.unsa.etf.pregledi_i_kartoni.repositories.PacijentKartonDoktorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PacijentKartonDoktorService {

    private final PacijentKartonDoktorRepository pacijentDoktorRepository;

    public String spasiPacijentDoktor(List<PacijentKartonDoktor> pacijentiDoktori) {
        pacijentDoktorRepository.deleteAllInBatch();
        pacijentDoktorRepository.flush();
        pacijentDoktorRepository.saveAll(pacijentiDoktori);
        return "Spremljeno!";
    }
}
