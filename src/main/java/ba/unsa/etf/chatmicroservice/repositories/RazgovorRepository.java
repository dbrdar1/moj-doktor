package ba.unsa.etf.chatmicroservice.repositories;

import ba.unsa.etf.chatmicroservice.models.Korisnik;
import ba.unsa.etf.chatmicroservice.models.Razgovor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RazgovorRepository extends JpaRepository<Razgovor, Long> {

    List<Razgovor> findAllByPrviKorisnik(Korisnik korisnik);
    List<Razgovor> findAllByDrugiKorisnik(Korisnik korisnik);
}
