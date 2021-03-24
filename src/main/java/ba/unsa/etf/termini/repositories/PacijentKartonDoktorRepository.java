package ba.unsa.etf.termini.repositories;

import ba.unsa.etf.termini.models.Doktor;
import ba.unsa.etf.termini.models.Korisnik;
import ba.unsa.etf.termini.models.Pacijent;
import ba.unsa.etf.termini.models.PacijentKartonDoktor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacijentKartonDoktorRepository extends JpaRepository<PacijentKartonDoktor, Long> {
    //PacijentKartonDoktor findByVeza(Doktor doktor, Pacijent pacijent);
}