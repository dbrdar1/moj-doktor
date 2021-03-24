package ba.unsa.etf.pregledi_i_kartoni.repositories;

import ba.unsa.etf.pregledi_i_kartoni.models.Doktor;
import ba.unsa.etf.pregledi_i_kartoni.models.KartonPacijent;
import ba.unsa.etf.pregledi_i_kartoni.models.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KartonPacijentRepository extends JpaRepository<KartonPacijent, Long>{
    Optional<KartonPacijent> findById(Long id);
}
