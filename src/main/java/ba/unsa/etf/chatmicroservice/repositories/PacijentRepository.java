package ba.unsa.etf.chatmicroservice.repositories;

import ba.unsa.etf.chatmicroservice.models.Pacijent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacijentRepository extends JpaRepository<Pacijent, Long> {
}