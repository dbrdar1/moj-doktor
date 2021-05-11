package ba.unsa.etf.chatmicroservice.service;

import ba.unsa.etf.chatmicroservice.exception.ResourceNotFoundException;
import ba.unsa.etf.chatmicroservice.model.*;
import ba.unsa.etf.chatmicroservice.repository.*;
import ba.unsa.etf.chatmicroservice.response.KorisnikResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;
    private final DoktorRepository doktorRepository;
    private final PacijentRepository pacijentRepository;
    private final NotifikacijaRepository notifikacijaRepository;
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

        porukaRepository.deleteAllInBatch();
        porukaRepository.flush();

        Date date = new Date();

        Doktor doktor = new Doktor(
                "Samra",
                "Pusina",
                date,
                "NekaAdresa1",
                "spusina1@etf.unsa.ba",
                "061111222");

        Pacijent pacijent = new Pacijent(
                "Esmina",
                "Radusic",
                date,
                "NekaAdresa2",
                "eradusic1@etf.unsa.ba",
                "061111222");

        Notifikacija notifikacija = new Notifikacija(
                "naziv notifikacije",
                "imate novu poruku",
                date,
                "14:00",
                pacijent);

        Poruka poruka = new Poruka(
                "dje si",
                0,
                date,
                "13:00",
                doktor,
                pacijent);

        doktorRepository.save(doktor);
        pacijentRepository.save(pacijent);
        notifikacijaRepository.save(notifikacija);
        porukaRepository.save(poruka);

        return "Inicijalizacija baze završena!";
    }

    public KorisnikResponse dajKorisnika(Long id) {
        Korisnik korisnik = korisnikRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ne postoji korisnik s ovim id-om!"));
        return new KorisnikResponse(
                korisnik.getIme(),
                korisnik.getPrezime(),
                korisnik.getAdresa(),
                korisnik.getBrojTelefona(),
                korisnik.getEmail()
        );
    }
}
