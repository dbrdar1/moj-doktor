package ba.unsa.etf.chatmicroservice.services;

import ba.unsa.etf.chatmicroservice.models.Poruka;
import ba.unsa.etf.chatmicroservice.repositories.PorukaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PorukaService {

    private final PorukaRepository porukaRepository;

    public String spasiPoruke(List<Poruka> poruke) {
        porukaRepository.deleteAllInBatch();
        porukaRepository.flush();
        porukaRepository.saveAll(poruke);
        return "Spremljeno!";
    }
}
