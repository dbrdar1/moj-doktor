package ba.unsa.etf.pregledi_i_kartoni.services;

import ba.unsa.etf.pregledi_i_kartoni.exceptions.ResourceNotFoundException;
import ba.unsa.etf.pregledi_i_kartoni.models.*;
import ba.unsa.etf.pregledi_i_kartoni.repositories.*;
import ba.unsa.etf.pregledi_i_kartoni.requests.PregledRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PregledService {

    private final PregledRepository pregledRepository;
    private final DoktorRepository doktorRepository;
    private final KartonPacijentRepository kartonPacijentRepository;
    private final KorisnikRepository korisnikRepository;
    private final PacijentKartonDoktorRepository pacijentKartonDoktorRepository;
    private final TerminRepository terminRepository;

    public String incijalizirajBazu() {

        korisnikRepository.deleteAllInBatch();
        korisnikRepository.flush();

        doktorRepository.deleteAllInBatch();
        doktorRepository.flush();

        kartonPacijentRepository.deleteAllInBatch();
        kartonPacijentRepository.flush();

        pacijentKartonDoktorRepository.deleteAllInBatch();
        pacijentKartonDoktorRepository.flush();

        terminRepository.deleteAllInBatch();
        terminRepository.flush();

        pregledRepository.deleteAllInBatch();
        pregledRepository.flush();

        Doktor doktor1 = new Doktor("ImeDoktora1", "PrezimeDoktora1", new Date(), "AdresaDoktora1", "061-123-123", "nekimaildoktora1@gmail.com");
        KartonPacijent pacijent1 = new KartonPacijent( "Ime1", "Prezime1", new Date(), "Adresa1", "061-456-456", "nekimailp1@gmail.com",
                "zenski", 175.2, 68.3, "0+", "/", "/");

        Doktor doktor2 = new Doktor("ImeDoktora2", "PrezimeDoktora2", new Date(), "AdresaDoktora2", "061-723-723", "nekimaildoktora2@gmail.com");
        KartonPacijent pacijent2 = new KartonPacijent( "Ime2", "Prezime2", new Date(), "Adresa2", "061-323-323", "nekimailp2@gmail.com",
                "zenski", 165.4, 56.3, "A-", "/", "/");

        PacijentKartonDoktor pd1 = new PacijentKartonDoktor(doktor1, pacijent1);
        PacijentKartonDoktor pd2 = new PacijentKartonDoktor(doktor2, pacijent2);



        Termin termin1 = new Termin(new Date(), "15:20", pd1);
        Termin termin2 = new Termin(new Date(), "16:30", pd2);


        Pregled pregled1 = new Pregled("neki simptomi", "neki fiz pregled", "neka dijagnoza",
                "neki tretman", "neki komentar", termin1);

        Pregled pregled2 = new Pregled("neki simptomi2", "neki fiz pregled2", "neka dijagnoza2",
                "neki tretman2", "neki komentar2", termin2);

        /*List<Pregled> pregledi = new ArrayList<>();

        pregledi.add(pregled1);
        pregledi.add(pregled2);

        */

        pregledRepository.save(pregled1);
        pregledRepository.save(pregled2);

        return "Inicijalizacija baze podataka završena!";
    }

    public Pregled getPregledById(Long id) {
        String errorMessage = String.format("Ne postoji pregled sa id = '%d'", id);
        return pregledRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }


    public List<Pregled> getAllPregled() {
        return pregledRepository
                .findAll();
    }

    public List<Pregled> getPregledByIdDoktoraIPacijenta(Long idDoktor, Long idPacijent) {
        return pregledRepository.findByIdDoktoraIPacijenta(idDoktor, idPacijent);
    }


}
