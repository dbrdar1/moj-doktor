package ba.unsa.etf.chatmicroservice.repositories;

import ba.unsa.etf.chatmicroservice.models.Razgovor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RazgovorRepository extends JpaRepository<Razgovor, Long>{

}
