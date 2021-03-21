package ba.unsa.etf.chatmicroservice.services;

import ba.unsa.etf.chatmicroservice.models.*;
import ba.unsa.etf.chatmicroservice.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;
    private final DoktorRepository doktorRepository;
    private final PacijentRepository pacijentRepository;
    private final NotifikacijaRepository notifikacijaRepository;
    private final RazgovorRepository razgovorRepository;
    private final PorukaRepository porukaRepository;


    public String inicijalizirajBazu() {

        korisnikRepository.deleteAllInBatch();
        korisnikRepository.flush();

        doktorRepository.deleteAllInBatch();
        doktorRepository.flush();

        pacijentRepository.deleteAllInBatch();
        pacijentRepository.flush();

        notifikacijaRepository.deleteAllInBatch();
        notifikacijaRepository.flush();

        razgovorRepository.deleteAllInBatch();
        razgovorRepository.flush();

        porukaRepository.deleteAllInBatch();
        porukaRepository.flush();

        Date date = new Date();

        Doktor doktor = new Doktor(
                "Samra",
                "Pusina",
                date,
                "NekaAdresa1",
                "061456321",
                "spusina1@etf.unsa.ba");

        Pacijent pacijent = new Pacijent(
                "Esmina",
                "Radusic",
                date,
                "NekaAdresa2",
                "061456322",
                "eradusic1@etf.unsa.ba");

        Notifikacija notifikacija = new Notifikacija(
                "naziv notifikacije",
                "imate novu poruku",
                date,
                "14:00",
                pacijent);

        Razgovor razgovor = new Razgovor(doktor, pacijent);

        Poruka poruka = new Poruka(
                "dje si",
                0,
                1,
                date,
                "14:00",
                razgovor);

        doktorRepository.save(doktor);
        pacijentRepository.save(pacijent);
        notifikacijaRepository.save(notifikacija);
        razgovorRepository.save(razgovor);
        porukaRepository.save(poruka);

        return "Inicijalizacija baze završena!";
    }
}
