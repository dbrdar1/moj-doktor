package ba.unsa.etf.usermanagement.repositories;

import ba.unsa.etf.usermanagement.models.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long>{

}
