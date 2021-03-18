package ba.unsa.etf.termini.repositories;

import ba.unsa.etf.termini.models.Termin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminRepository extends JpaRepository<Termin, Long> {

}