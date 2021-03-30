package ba.unsa.etf.pregledi_i_kartoni.services;

import ba.unsa.etf.pregledi_i_kartoni.exceptions.ResourceNotFoundException;
import ba.unsa.etf.pregledi_i_kartoni.models.PacijentDoktor;
import ba.unsa.etf.pregledi_i_kartoni.models.Pregled;
import ba.unsa.etf.pregledi_i_kartoni.models.Termin;
import ba.unsa.etf.pregledi_i_kartoni.models.Termin;
import ba.unsa.etf.pregledi_i_kartoni.repositories.PacijentDoktorRepository;
import ba.unsa.etf.pregledi_i_kartoni.repositories.TerminRepository;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajTerminRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.TerminResponse;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import ba.unsa.etf.pregledi_i_kartoni.responses.TerminResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TerminService {

    private final TerminRepository terminRepository;
    private final PacijentDoktorRepository pacijentDoktorRepository;

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
        if(!pacijentDoktorVeza.isPresent()) return new Response("Ne postoji veza pacijent-doktor za dati termin!", 400);
        Termin noviTermin = new Termin(
                dodajTerminRequest.getDatumPregleda(), dodajTerminRequest.getVrijemePregleda(), pacijentDoktorVeza.get()
        );
        terminRepository.save(noviTermin);
        return new Response("Uspješno ste dodali termin termina!", 200);
    }

    public Response obrisiTermin(Long id) {

        String errorBrisanjeTermina = String.format("Ne postoji termin sa id = '%d'", id);
        Optional<Termin> trazeniTermin = terminRepository.findById(id);
        if(!trazeniTermin.isPresent()) {
            return new Response(errorBrisanjeTermina, 400);
        }
        terminRepository.deleteById(id);
        return new Response("Uspješno ste obrisali pregled!", 200);

    }

}
