package ba.unsa.etf.termini.repositories;

import ba.unsa.etf.termini.models.Notifikacija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifikacijaRepository extends JpaRepository<Notifikacija, Long> {

}
