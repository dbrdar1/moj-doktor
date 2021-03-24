package ba.unsa.etf.pregledi_i_kartoni.repositories;

import ba.unsa.etf.pregledi_i_kartoni.models.Doktor;
import ba.unsa.etf.pregledi_i_kartoni.models.KartonPacijent;
import ba.unsa.etf.pregledi_i_kartoni.models.Korisnik;
import ba.unsa.etf.pregledi_i_kartoni.models.Pregled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PregledRepository extends JpaRepository<Pregled, Long>{
    Optional<Pregled> findById(Long id);

    /*String upitPregledDoktorPacijent = "SELECT* FROM pregled WHERE " +
            "termin_id IS NOT NULL AND termin_id in (SELECT id from termin" +
            "WHERE pacijent_doktor_id IN (SELECT id FROM pacijent_karton_doktor WHERE " +
            "doktor_id = ?1 AND pacijent_id = ?2))";*/


    /*
    @Query(value = "SELECT* FROM pregled WHERE " +
            "termin_id IS NOT NULL",
            nativeQuery=true)

     */
    @Query(value = "SELECT* FROM pregled WHERE " +
            "termin_id IS NOT NULL AND termin_id IN (SELECT id from termin " +
            "WHERE pacijent_doktor_id IN (SELECT id FROM pacijent_karton_doktor WHERE " +
            "doktor_id = ?1 AND pacijent_id = ?2))",
            nativeQuery=true)
    List<Pregled> findByIdDoktoraIPacijenta(Long idDoktor, Long idPacijent);
}
