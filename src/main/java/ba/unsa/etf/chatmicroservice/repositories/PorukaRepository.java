package ba.unsa.etf.chatmicroservice.repositories;

import ba.unsa.etf.chatmicroservice.models.Korisnik;
import ba.unsa.etf.chatmicroservice.models.Poruka;
import ba.unsa.etf.chatmicroservice.projections.PorukaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PorukaRepository extends JpaRepository<Poruka, Long> {

    List<PorukaProjection> findAllByPosiljalacAndPrimalac(Korisnik posiljalac, Korisnik primalac);
    PorukaProjection findBySadrzaj(String sadrzaj);
}
