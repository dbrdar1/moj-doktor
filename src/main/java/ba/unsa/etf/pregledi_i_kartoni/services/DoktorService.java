package ba.unsa.etf.pregledi_i_kartoni.services;

import ba.unsa.etf.pregledi_i_kartoni.exceptions.ResourceNotFoundException;
import ba.unsa.etf.pregledi_i_kartoni.models.Doktor;
import ba.unsa.etf.pregledi_i_kartoni.models.KartonPacijent;
import ba.unsa.etf.pregledi_i_kartoni.models.Korisnik;
import ba.unsa.etf.pregledi_i_kartoni.repositories.DoktorRepository;
import ba.unsa.etf.pregledi_i_kartoni.repositories.KorisnikRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@AllArgsConstructor
@Service
public class DoktorService {

    private final DoktorRepository doktorRepository;

    public String spasiDoktore(List<Doktor> doktori) {
        doktorRepository.deleteAllInBatch();
        doktorRepository.flush();
        doktorRepository.saveAll(doktori);
        return "Spremljeno!";
    }


    public Doktor getDoktorById(Long id) {
        String errorMessage = String.format("Ne postoji doktor sa id = '%d'", id);
        return doktorRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }

    public List<Doktor> getAllDoktor() {
        return doktorRepository
                .findAll();
    }
}
