package ba.unsa.etf.chatmicroservice.services;

import ba.unsa.etf.chatmicroservice.models.Notifikacija;
import ba.unsa.etf.chatmicroservice.repositories.NotifikacijaRepository;
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
        return "Spremljeno!";
    }
}
