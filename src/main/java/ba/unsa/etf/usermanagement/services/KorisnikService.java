package ba.unsa.etf.usermanagement.services;

import ba.unsa.etf.usermanagement.models.Korisnik;
import ba.unsa.etf.usermanagement.repositories.KorisnikRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;

    public String spasiKorisnike(List<Korisnik> korisnici) {
        korisnikRepository.deleteAllInBatch();
        korisnikRepository.flush();
        korisnikRepository.saveAll(korisnici);
        return "Spremljeno!";
    }
}
