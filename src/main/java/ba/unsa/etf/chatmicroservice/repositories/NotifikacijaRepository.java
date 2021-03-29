package ba.unsa.etf.chatmicroservice.repositories;

import ba.unsa.etf.chatmicroservice.models.Korisnik;
import ba.unsa.etf.chatmicroservice.models.Notifikacija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotifikacijaRepository extends JpaRepository<Notifikacija, Long>{

    List<Notifikacija> findAllByKorisnik(Korisnik korisnik);
    Notifikacija findByNaslov(String naslov);
}
