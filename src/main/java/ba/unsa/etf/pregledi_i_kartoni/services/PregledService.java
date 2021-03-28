package ba.unsa.etf.pregledi_i_kartoni.services;

import ba.unsa.etf.pregledi_i_kartoni.exceptions.ResourceNotFoundException;
import ba.unsa.etf.pregledi_i_kartoni.models.*;
import ba.unsa.etf.pregledi_i_kartoni.repositories.*;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajPregledRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PregledService {

    private final PregledRepository pregledRepository;
    private final DoktorRepository doktorRepository;
    private final PacijentRepository pacijentRepository;
    private final KorisnikRepository korisnikRepository;
    private final PacijentDoktorRepository pacijentDoktorRepository;
    private final TerminRepository terminRepository;

    public String incijalizirajBazu() {

        korisnikRepository.deleteAllInBatch();
        korisnikRepository.flush();

        doktorRepository.deleteAllInBatch();
        doktorRepository.flush();

        pacijentRepository.deleteAllInBatch();
        pacijentRepository.flush();

        pacijentDoktorRepository.deleteAllInBatch();
        pacijentDoktorRepository.flush();

        terminRepository.deleteAllInBatch();
        terminRepository.flush();

        pregledRepository.deleteAllInBatch();
        pregledRepository.flush();

        Doktor doktor1 = new Doktor("ImeDoktora1", "PrezimeDoktora1", new Date(), "AdresaDoktora1", "061-123-123", "nekimaildoktora1@gmail.com");
        Pacijent pacijent1 = new Pacijent( "ImePacijenta1", "PrezimePacijenta1", new Date(), "Adresa1", "061-456-456", "nekimailpacijenta1@gmail.com",
                "zenski", 175.2, 68.3, "0+", "/", "/");

        Doktor doktor2 = new Doktor("ImeDoktora2", "PrezimeDoktora2", new Date(), "AdresaDoktora2", "061-723-723", "nekimaildoktora2@gmail.com");
        Pacijent pacijent2 = new Pacijent( "ImePacijenta2", "PrezimePacijenta2", new Date(), "Adresa2", "061-323-323", "nekimailpacijenta2@gmail.com",
                "zenski", 165.4, 56.3, "A-", "/", "/");

        PacijentDoktor pd1 = new PacijentDoktor(doktor1, pacijent1);
        PacijentDoktor pd2 = new PacijentDoktor(doktor2, pacijent2);



        Termin termin1 = new Termin(new Date(), "15:20", pd1);
        Termin termin2 = new Termin(new Date(), "16:30", pd2);


        Pregled pregled1 = new Pregled("neki simptomi1", "neki fiz pregled1", "neka dijagnoza1",
                "neki tretman1", "neki komentar1", termin1);

        Pregled pregled2 = new Pregled("neki simptomi2", "neki fiz pregled2", "neka dijagnoza2",
                "neki tretman2", "neki komentar2", termin2);

        pregledRepository.save(pregled1);
        pregledRepository.save(pregled2);

        return "Inicijalizacija baze podataka završena!";
    }

    public Pregled dajPregledNaOsonvuId(Long id) {
        String errorMessage = String.format("Ne postoji pregled sa id = '%d'", id);
        return pregledRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }


    public List<Pregled> dajSvePreglede() {
        return pregledRepository
                .findAll();
    }


    public List<Pregled> dajPregledeNaOsnovuIdDoktoraIdPacijenta(Long idDoktor, Long idPacijent) {
        return pregledRepository.findByIdDoktoraIPacijenta(idDoktor, idPacijent);
    }


    public Response dodajPregled(DodajPregledRequest dodajPregledRequest) {
        Optional<Termin> terminPregleda = terminRepository.findById(dodajPregledRequest.getTerminId());
        if(!terminPregleda.isPresent()) return new Response("Ne postoji zakazani termin za dati pregled!", 400);
        Pregled noviPregled = new Pregled(
                dodajPregledRequest.getSimptomi(), dodajPregledRequest.getFizikalniPregled(), dodajPregledRequest.getDijagnoza(),
                dodajPregledRequest.getTretman(), dodajPregledRequest.getKomentar(), terminPregleda.get()
        );
        //terminPregleda.get().getPregledi().add(noviPregled);
        pregledRepository.save(noviPregled);
        return new Response("Uspješno ste dodali pregled!", 200);
    }


}
