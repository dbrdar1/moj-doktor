package ba.unsa.etf.chatmicroservice.services;

import ba.unsa.etf.chatmicroservice.models.Razgovor;
import ba.unsa.etf.chatmicroservice.repositories.RazgovorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RazgovorService {

    private final RazgovorRepository razgovorRepository;

    public String spasiRazgovore(List<Razgovor> razgovori) {
        razgovorRepository.deleteAllInBatch();
        razgovorRepository.flush();
        razgovorRepository.saveAll(razgovori);
        return "Spremljeno!";
    }
}
