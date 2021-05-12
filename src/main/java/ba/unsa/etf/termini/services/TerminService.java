package ba.unsa.etf.termini.services;

import ba.unsa.etf.termini.Requests.DodajTerminRequest;
import ba.unsa.etf.termini.Requests.UrediTerminRequest;
import ba.unsa.etf.termini.Responses.ListaTerminaResponse;
import ba.unsa.etf.termini.Responses.Response;
import ba.unsa.etf.termini.Responses.TerminResponse;
import ba.unsa.etf.termini.exceptions.ResourceNotFoundException;
import ba.unsa.etf.termini.models.*;
import ba.unsa.etf.termini.repositories.DoktorRepository;
import ba.unsa.etf.termini.repositories.PacijentKartonDoktorRepository;
import ba.unsa.etf.termini.repositories.PacijentRepository;
import ba.unsa.etf.termini.repositories.TerminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TerminService {
    private TerminRepository terminRepository;
    private PacijentKartonDoktorRepository pacijentKartonDoktorRepository;
    private PacijentRepository pacijentRepository;
    private DoktorRepository doktorRepository;

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
                new ResourceNotFoundException("Ne postoji termin s ovim id-om!"));
        terminRepository.deleteById(id);
        return new Response("Uspješno ste obrisali termin!", 200);
    }

    public Response urediTermin(Long id, UrediTerminRequest urediTerminRequest) {
        Termin termin = terminRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Ne postoji termin s ovim id-om!"));
        termin.setDatum(urediTerminRequest.getDatum());
        termin.setVrijeme(urediTerminRequest.getVrijeme());
        terminRepository.save(termin);
        return new Response("Uspješno ste uredili termin!",200);
    }

    public ListaTerminaResponse dajTerminePacijenta(Long id) {
        Pacijent p = pacijentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Ne postoji pacijent s ovim id-om!"));
        List<PacijentKartonDoktor> veze = pacijentKartonDoktorRepository.findAllByPacijent(p);
        List<Termin> sviTermini = new ArrayList<>();
        for (int i =0; i< veze.size();i++){
            List<Termin> t = terminRepository.findAllByPacijentKartonDoktor(veze.get(i));
            sviTermini.addAll(t);
        }
        return  new ListaTerminaResponse(sviTermini);
    }

    public ListaTerminaResponse dajTermineDoktora(Long id) {
        Doktor d = doktorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Ne postoji doktor s ovim id-om!"));
        List<PacijentKartonDoktor> veze = pacijentKartonDoktorRepository.findAllByDoktor(d);
        List<Termin> sviTermini = new ArrayList<>();
        for (int i =0; i< veze.size();i++){
            List<Termin> t = terminRepository.findAllByPacijentKartonDoktor(veze.get(i));
            sviTermini.addAll(t);
        }
        return  new ListaTerminaResponse(sviTermini);
    }

    public TerminResponse dajTermin(Long id) {
        Termin t = terminRepository.findById(id).orElseThrow(() -> new ba.unsa.etf.termini.exceptions.ResourceNotFoundException("Ne postoji termin s ovim id-om!"));
        return new TerminResponse(t.getDatum(),t.getVrijeme());
    }

//    public Response obrisiTerminPoAtributima(ObrisiTerminRequest obrisiTerminRequest) {
//        Termin t;
//        try{
//            t= terminRepository.findByDatumAndVrijeme(obrisiTerminRequest.getDatum(), obrisiTerminRequest.getVrijeme());
//        } catch (Exception e){
//            throw new ResourceNotFoundException("Ne postoji termin s ovim datumom i vremenom!");
//        }
//        terminRepository.delete(t);
//        return new Response("Termin uspjesno obrisan!");
//    }
}
