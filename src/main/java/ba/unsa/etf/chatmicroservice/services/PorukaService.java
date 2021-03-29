package ba.unsa.etf.chatmicroservice.services;

import ba.unsa.etf.chatmicroservice.exceptions.ResourceNotFoundException;
import ba.unsa.etf.chatmicroservice.models.Poruka;
import ba.unsa.etf.chatmicroservice.repositories.PorukaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PorukaService {

    private final PorukaRepository porukaRepository;

    public Poruka dajPoruku(Long id) {
        Poruka poruka = porukaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ne postoji poruka s ovim id-om!"));
        return poruka;
    }
}
