package ba.unsa.etf.termini.services;

import ba.unsa.etf.termini.Requests.DodajTerminRequest;
import ba.unsa.etf.termini.Responses.Response;
import ba.unsa.etf.termini.exceptions.ResourceNotFoundException;
import ba.unsa.etf.termini.models.Korisnik;
import ba.unsa.etf.termini.models.Notifikacija;
import ba.unsa.etf.termini.models.PacijentKartonDoktor;
import ba.unsa.etf.termini.models.Termin;
import ba.unsa.etf.termini.repositories.PacijentKartonDoktorRepository;
import ba.unsa.etf.termini.repositories.TerminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TerminService {
    private TerminRepository terminRepository;
    private PacijentKartonDoktorRepository pacijentKartonDoktorRepository;


    public Response dodajTermin(DodajTerminRequest dodajTerminRequest) {
        Optional<PacijentKartonDoktor> pkd = pacijentKartonDoktorRepository.findById(dodajTerminRequest.getIdPkd());
        if(!pkd.isPresent()) return new Response("Id veze nije postojeći!", 400);
        Termin termin= new Termin(dodajTerminRequest.getDatum(), dodajTerminRequest.getVrijeme(), pkd.get());
        pkd.get().getTermini().add(termin);
        pacijentKartonDoktorRepository.save(pkd.get());
        return new Response("Uspješno ste dodali termin!", 200);
    }

    public Response obrisiTermin(Long id) {
        Termin termin = terminRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Ne postoji termin s ovim id-om!"));;
        terminRepository.deleteById(id);
        return new Response("Uspješno ste obrisali termin!", 200);
    }
}
