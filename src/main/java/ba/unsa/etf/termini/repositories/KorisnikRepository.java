package ba.unsa.etf.termini.repositories;

import ba.unsa.etf.termini.models.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long>{

}
