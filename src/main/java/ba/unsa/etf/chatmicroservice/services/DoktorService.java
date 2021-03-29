package ba.unsa.etf.chatmicroservice.services;

import ba.unsa.etf.chatmicroservice.exceptions.ResourceNotFoundException;
import ba.unsa.etf.chatmicroservice.models.Doktor;
import ba.unsa.etf.chatmicroservice.repositories.DoktorRepository;
import ba.unsa.etf.chatmicroservice.responses.DoktorResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DoktorService {

    private final DoktorRepository doktorRepository;

    public DoktorResponse dajDoktora(Long id) {
        Doktor d = doktorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji doktor s ovim id-om!"));
        return new DoktorResponse(d.getIme(),d.getPrezime(),d.getAdresa(),d.getBrojTelefona(),d.getEmail());
    }
}
