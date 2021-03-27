package ba.unsa.etf.termini.repositories;

import ba.unsa.etf.termini.models.PacijentKartonDoktor;
import ba.unsa.etf.termini.models.Termin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerminRepository extends JpaRepository<Termin, Long> {
    List<Termin> findAllByPacijentKartonDoktor(PacijentKartonDoktor pkd);
}