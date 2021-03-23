package ba.unsa.etf.doktordetalji.services;

import ba.unsa.etf.doktordetalji.exceptions.ResourceNotFoundException;
import ba.unsa.etf.doktordetalji.models.Certifikat;
import ba.unsa.etf.doktordetalji.models.Doktor;
import ba.unsa.etf.doktordetalji.models.Edukacija;
import ba.unsa.etf.doktordetalji.models.Ocjena;
import ba.unsa.etf.doktordetalji.repositories.CertifikatRepository;
import ba.unsa.etf.doktordetalji.repositories.DoktorRepository;
import ba.unsa.etf.doktordetalji.repositories.EdukacijaRepository;
import ba.unsa.etf.doktordetalji.requests.*;
import ba.unsa.etf.doktordetalji.responses.CertifikatiResponse;
import ba.unsa.etf.doktordetalji.responses.DoktorCVResponse;
import ba.unsa.etf.doktordetalji.responses.EdukacijeResponse;
import ba.unsa.etf.doktordetalji.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DoktorService {

    private final DoktorRepository doktorRepository;
    private final EdukacijaRepository edukacijaRepository;
    private final CertifikatRepository certifikatRepository;

    public List<Doktor> getDoktori(FilterRequest filterRequest) {
        return doktorRepository.findByFilter(filterRequest);
    }

    public DoktorCVResponse getDoktorCV(Long id) {

        Doktor d = doktorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ne postoji doktor s ovim id-om!"));;
        List<Edukacija> edukacije = edukacijaRepository.findByDoktor(d);
        List<Certifikat> certifikati = certifikatRepository.findByDoktor(d);

        return new DoktorCVResponse(
                d.getIme(),
                d.getPrezime(),
                d.getAdresa(),
                d.getEmail(),
                d.getTitula(),
                d.getBiografija(),
                prosjecnaOcjena(d),
                edukacije
                        .stream()
                        .map(e -> new EdukacijeResponse(e.getInstitucija(), e.getOdsjek(), e.getStepen(), e.getGodinaPocetka(), e.getGodinaZavrsetka()))
                        .collect(Collectors.toList()),
                certifikati
                        .stream()
                        .map(c -> new CertifikatiResponse(c.getInstitucija(), c.getNaziv(), c.getGodinaIzdavanja()))
                        .collect(Collectors.toList())
        );
    }

    public Response ocjeni(OcjenaRequest ocjenaRequest) {
        Optional<Doktor> d = doktorRepository.findById(ocjenaRequest.getId());
        if(!d.isPresent()) return new Response("Id doktora nije postojeći!", 400);
        Ocjena ocjena = new Ocjena(ocjenaRequest.getOcjena());
        ocjena.setDoktor(d.get());
        d.get().getOcjene().add(ocjena);
        doktorRepository.save(d.get());
        return new Response("Uspješno ste ocijenili doktora!", 200);
    }

    public Double prosjecnaOcjena(Doktor doktor){
        List<Ocjena> ocjene = doktor.getOcjene();
        Double suma = 0.0;
        for (Ocjena ocjena : ocjene) {
            suma += ocjena.getVrijednost();
        }
         return suma/ocjene.size();
    }

    public Response dodajCertifikat(DodajCertifikatRequest dodajCertifikatRequest) {
        Optional<Doktor> d = doktorRepository.findById(dodajCertifikatRequest.getIdDoktora());
        if(!d.isPresent()) return new Response("Id doktora nije postojeći!", 400);
        Certifikat c = new Certifikat(dodajCertifikatRequest.getInstitucija(), dodajCertifikatRequest.getNaziv(), dodajCertifikatRequest.getGodinaIzdavanja());
        c.setDoktor(d.get());
        d.get().getCertifikati().add(c);
        doktorRepository.save(d.get());
        return new Response("Uspješno ste dodali certifikat!", 200);

    }

    public Response urediCertifikat(UrediCertifikatRequest urediCertifikatRequest) {
        Optional<Certifikat> c = certifikatRepository.findById(urediCertifikatRequest.getId());
        if(!c.isPresent()) return new Response("Nepostojeći Id certifikata!", 400);
        c.get().setInstitucija(urediCertifikatRequest.getInstitucija());
        c.get().setNaziv(urediCertifikatRequest.getNaziv());
        c.get().setGodinaIzdavanja(urediCertifikatRequest.getGodinaIzdavanja());
        certifikatRepository.save(c.get());
        return new Response("Uspješno ste uredili certifikat!", 200);
    }

    public Response obrisiCertifikat(Long id) {
        Optional<Certifikat> c = certifikatRepository.findById(id);
        if(!c.isPresent()) return new Response("Nepostojeći Id certifikata!", 400);
        certifikatRepository.delete(c.get());
        return new Response("Uspješno ste obrisali certifikat!", 200);
    }

    public Response dodajEdukaciju(DodajEdukacijuRequest dodajEdukacijuRequest) {
        Optional<Doktor> d = doktorRepository.findById(dodajEdukacijuRequest.getIdDoktora());
        if(!d.isPresent()) return new Response("Id doktora nije postojeći!", 400);
        Edukacija c = new Edukacija
                (
                dodajEdukacijuRequest.getInstitucija(),
                dodajEdukacijuRequest.getOdsjek(),
                dodajEdukacijuRequest.getStepen(),
                dodajEdukacijuRequest.getGodinaPocetka(),
                dodajEdukacijuRequest.getGodinaZavrsetka(),
                dodajEdukacijuRequest.getGrad(),
                dodajEdukacijuRequest.getDrzava()
                );
        c.setDoktor(d.get());
        d.get().getEdukacije().add(c);
        doktorRepository.save(d.get());
        return new Response("Uspješno ste dodali edukaciju!", 200);
    }

    public Response urediEdukaciju(UrediEdukacijuRequest urediEdukacijuRequest) {
        Optional<Edukacija> c = edukacijaRepository.findById(urediEdukacijuRequest.getId());
        if(!c.isPresent()) return new Response("Nepostojeći Id edukacije!", 400);
        c.get().setInstitucija(urediEdukacijuRequest.getInstitucija());
        c.get().setOdsjek(urediEdukacijuRequest.getOdsjek());
        c.get().setStepen(urediEdukacijuRequest.getStepen());
        c.get().setGodinaPocetka(urediEdukacijuRequest.getGodinaPocetka());
        c.get().setGodinaZavrsetka(urediEdukacijuRequest.getGodinaZavrsetka());
        c.get().setGrad(urediEdukacijuRequest.getGrad());
        c.get().setDrzava(urediEdukacijuRequest.getDrzava());
        edukacijaRepository.save(c.get());
        return new Response("Uspješno ste uredili edukaciju!", 200);
    }

    public Response obrisiEdukaciju(Long id) {
        Optional<Edukacija> c = edukacijaRepository.findById(id);
        if(!c.isPresent()) return new Response("Nepostojeći Id edukacije!", 400);
        edukacijaRepository.delete(c.get());
        return new Response("Uspješno ste obrisali edukaciju!", 200);
    }

    public Response urediPodatkeDoktora(UrediPodatkeDoktoraRequest urediPodatkeDoktoraRequest) {
        Optional<Doktor> d = doktorRepository.findById(urediPodatkeDoktoraRequest.getIdDoktora());
        if(!d.isPresent()) return new Response("Id doktora nije postojeći!", 400);
        d.get().setTitula(urediPodatkeDoktoraRequest.getTitula());
        d.get().setBiografija(urediPodatkeDoktoraRequest.getBiografija());
        return new Response("Uspješno ste uredili osnovne podatke doktora!", 200);
    }
}

