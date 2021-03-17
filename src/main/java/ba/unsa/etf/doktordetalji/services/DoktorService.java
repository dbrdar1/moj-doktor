package ba.unsa.etf.doktordetalji.services;

import ba.unsa.etf.doktordetalji.models.Doktor;
import ba.unsa.etf.doktordetalji.repositories.DoktorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DoktorService {

    private DoktorRepository doktorRepository;
    public List<Doktor> getDoktori() {
        return  doktorRepository.findAll();
    }
}
