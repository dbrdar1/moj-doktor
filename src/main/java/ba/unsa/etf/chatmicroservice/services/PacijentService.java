package ba.unsa.etf.chatmicroservice.services;

import ba.unsa.etf.chatmicroservice.exceptions.ResourceNotFoundException;
import ba.unsa.etf.chatmicroservice.models.Pacijent;
import ba.unsa.etf.chatmicroservice.repositories.PacijentRepository;
import ba.unsa.etf.chatmicroservice.responses.PacijentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PacijentService {

    private final PacijentRepository pacijentRepository;

    public PacijentResponse dajPacijenta(Long id) {
        Pacijent p = pacijentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji pacijent s ovim id-om!"));
        return new PacijentResponse(p.getIme(),p.getPrezime(),p.getAdresa(),p.getBrojTelefona(),p.getEmail());
    }
}
