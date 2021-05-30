package ba.unsa.etf.pregledi_i_kartoni.services;

import ba.unsa.etf.pregledi_i_kartoni.exceptions.ResourceNotFoundException;
import ba.unsa.etf.pregledi_i_kartoni.models.*;
import ba.unsa.etf.pregledi_i_kartoni.models.Termin;
import ba.unsa.etf.pregledi_i_kartoni.repositories.PacijentDoktorRepository;
import ba.unsa.etf.pregledi_i_kartoni.repositories.PacijentRepository;
import ba.unsa.etf.pregledi_i_kartoni.repositories.TerminRepository;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajTerminRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.*;
import ba.unsa.etf.pregledi_i_kartoni.responses.TerminResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TerminService {

    private final TerminRepository terminRepository;
    private final PacijentDoktorRepository pacijentDoktorRepository;
    private final PacijentRepository pacijentRepository;


    @Autowired
    private RestTemplate restTemplate;

    public String spasiTermine(List<Termin> termini) {
        terminRepository.deleteAllInBatch();
        terminRepository.flush();
        terminRepository.saveAll(termini);
        return "Spremljeno!";
    }

    public TerminResponse dajTerminNaOsnovuId(Long idTermina) {

        String errorMessageTermin = String.format("Ne postoji termin sa id = '%d'!", idTermina);
        Termin trazeniTermin = terminRepository.findById(idTermina).orElseThrow(() -> new ResourceNotFoundException(errorMessageTermin));
        return new TerminResponse(trazeniTermin.getId(), trazeniTermin.getDatumPregleda(), trazeniTermin.getVrijemePregleda(),
                trazeniTermin.getPacijentDoktor().getId()
        );

    }


    public List<TerminResponse> dajSveTermine() {
        List<Termin> termini = terminRepository.findAll();
        List<TerminResponse> listaTerminResponse = new ArrayList<>();
        for (Termin termin : termini) {
            listaTerminResponse.add(new TerminResponse(termin.getId(), termin.getDatumPregleda(), termin.getVrijemePregleda(),
                    termin.getPacijentDoktor().getId()
                    )
            );
        }

        return listaTerminResponse;

    }


    public Response dodajTermin(DodajTerminRequest dodajTerminRequest) {
        Optional<PacijentDoktor> pacijentDoktorVeza = pacijentDoktorRepository.findById(dodajTerminRequest.getPacijentDoktorId());
        if(!pacijentDoktorVeza.isPresent()) throw new ResourceNotFoundException("Ne postoji veza pacijent-doktor za dati termin!");
        Termin noviTermin = new Termin(
                dodajTerminRequest.getDatumPregleda(), dodajTerminRequest.getVrijemePregleda(), pacijentDoktorVeza.get()
        );
        pacijentDoktorVeza.get().getTermini().add(noviTermin);
        pacijentDoktorRepository.save(pacijentDoktorVeza.get());
        //terminRepository.save(noviTermin);
        return new Response("Uspješno ste dodali termin pregleda!", 200);
    }

    public Response obrisiTermin(Long id) {

        String errorBrisanjeTermina = String.format("Ne postoji termin sa id = '%d'", id);
        Optional<Termin> trazeniTermin = terminRepository.findById(id);
        if(!trazeniTermin.isPresent()) {
            throw new ResourceNotFoundException(errorBrisanjeTermina);
        }
        terminRepository.deleteById(id);
        return new Response("Uspješno ste obrisali pregled!", 200);

    }

    // Komunikacija sa "termini" mikroservisom
    public String dajTermin(Long idTermina) {

        String fooResourceUrl = "http://termini/dobavi-termin/" + idTermina.toString();
        ResponseEntity<String> responseString = restTemplate.getForEntity(fooResourceUrl, String.class);
        ResponseEntity<ListaTerminaResponse> response = restTemplate.getForEntity(fooResourceUrl, ListaTerminaResponse.class);
        return responseString.getStatusCode() == HttpStatus.OK ? responseString.getBody() : null;

    }

    public List<TerminResponse> dajTerminePacijenta(Long id) {
        Pacijent p = pacijentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Ne postoji pacijent sa id = " + id.toString() + "!"));
        if(!terminRepository.findByPacijent(p).isPresent()) {
            throw new ResourceNotFoundException("Pacijent nema nijedan zakazan termin!");
        }
        List<Termin> terminiPacijenta = terminRepository.findByPacijent(p).get();
        List<TerminResponse> listaTerminResponse = new ArrayList<>();
        for (Termin t : terminiPacijenta) {
            listaTerminResponse.add(new TerminResponse(t.getId(), t.getDatumPregleda(), t.getVrijemePregleda(), t.getPacijentDoktor().getId()));
        }
        return listaTerminResponse;

    }

}
