package ba.unsa.etf.pregledi_i_kartoni.services;

import ba.unsa.etf.pregledi_i_kartoni.exceptions.ResourceNotFoundException;
import ba.unsa.etf.pregledi_i_kartoni.models.Doktor;
import ba.unsa.etf.pregledi_i_kartoni.models.KartonPacijent;
import ba.unsa.etf.pregledi_i_kartoni.models.Korisnik;
import ba.unsa.etf.pregledi_i_kartoni.repositories.KartonPacijentRepository;
import ba.unsa.etf.pregledi_i_kartoni.repositories.KorisnikRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class KartonPacijentService {

    private final KartonPacijentRepository kartonPacijentRepository;

    public String pohraniPacijente(List<KartonPacijent> pacijenti) {
        kartonPacijentRepository.deleteAllInBatch();
        kartonPacijentRepository.flush();
        kartonPacijentRepository.saveAll(pacijenti);
        return "Spremljeno!";
    }

    public KartonPacijent getPacijentById(Long id) {
        String errorMessage = String.format("Ne postoji pacijent sa id = '%d'", id);
        return kartonPacijentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }

    public List<KartonPacijent> getAllPacijent() {
        return kartonPacijentRepository
                .findAll();
    }

}
