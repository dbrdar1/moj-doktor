package ba.unsa.etf.doktordetalji.services;

import ba.unsa.etf.doktordetalji.exceptions.ResourceNotFoundException;
import ba.unsa.etf.doktordetalji.exceptions.UnauthorizedException;
import ba.unsa.etf.doktordetalji.models.*;
import ba.unsa.etf.doktordetalji.repositories.CertifikatRepository;
import ba.unsa.etf.doktordetalji.repositories.DoktorRepository;
import ba.unsa.etf.doktordetalji.repositories.EdukacijaRepository;
import ba.unsa.etf.doktordetalji.repositories.KorisnikRepository;
import ba.unsa.etf.doktordetalji.requests.*;
import ba.unsa.etf.doktordetalji.responses.*;
import ba.unsa.etf.doktordetalji.security.TrenutniKorisnikSecurity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
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
    private final TrenutniKorisnikSecurity trenutniKorisnikSecurity;

    public List<Doktor> getDoktori(FilterRequest filterRequest) {
        List<Doktor> doktori = doktorRepository.findByFilter(filterRequest);
        if (doktori.size() == 0) throw new ResourceNotFoundException("Doktor nije pronađen!");
        return doktori;
    }

    public DoktorCVResponse getDoktorCV(Long id) {

        Doktor d = doktorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ne postoji doktor s ovim id-om!"));
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
        if (!d.isPresent()) return new Response("Id doktora nije postojeći!", 400);
        Ocjena ocjena = new Ocjena(ocjenaRequest.getOcjena());
        ocjena.setDoktor(d.get());
        d.get().getOcjene().add(ocjena);
        doktorRepository.save(d.get());
        return new Response("Uspješno ste ocijenili doktora!", 200);
    }

    public Double prosjecnaOcjena(Doktor doktor) {
        List<Ocjena> ocjene = doktor.getOcjene();
        Double suma = 0.0;
        for (Ocjena ocjena : ocjene) {
            suma += ocjena.getVrijednost();
        }
        return suma / ocjene.size();
    }

    public Response dodajCertifikat(DodajCertifikatRequest dodajCertifikatRequest) {
        Optional<Doktor> d = doktorRepository.findById(dodajCertifikatRequest.getIdDoktora());
        if (!d.isPresent()) return new Response("Id doktora nije postojeći!", 400);
        Certifikat c = new Certifikat(dodajCertifikatRequest.getInstitucija(), dodajCertifikatRequest.getNaziv(), dodajCertifikatRequest.getGodinaIzdavanja());
        c.setDoktor(d.get());
        d.get().getCertifikati().add(c);
        doktorRepository.save(d.get());
        return new Response("Uspješno ste dodali certifikat!", 200);

    }

    public Response urediCertifikat(HttpHeaders headers, UrediCertifikatRequest urediCertifikatRequest) {
        Optional<Certifikat> c = certifikatRepository.findById(urediCertifikatRequest.getId());
        if (!c.isPresent()) return new Response("Nepostojeći Id certifikata!", 400);

        if(!trenutniKorisnikSecurity.isTrenutniKorisnik(headers, c.get().getDoktor().getId()))
            throw new UnauthorizedException("Neovlašten pristup resursima!");

        c.get().setInstitucija(urediCertifikatRequest.getInstitucija());
        c.get().setNaziv(urediCertifikatRequest.getNaziv());
        c.get().setGodinaIzdavanja(urediCertifikatRequest.getGodinaIzdavanja());
        certifikatRepository.save(c.get());
        return new Response("Uspješno ste uredili certifikat!", 200);
    }

    public Response obrisiCertifikat(HttpHeaders headers, Long id) {
        Optional<Certifikat> c = certifikatRepository.findById(id);
        if (!c.isPresent()) return new Response("Nepostojeći Id certifikata!", 400);

        if(!trenutniKorisnikSecurity.isTrenutniKorisnik(headers, c.get().getDoktor().getId()))
            throw new UnauthorizedException("Neovlašten pristup resursima!");

        certifikatRepository.delete(c.get());
        return new Response("Uspješno ste obrisali certifikat!", 200);
    }

    public Response dodajEdukaciju(DodajEdukacijuRequest dodajEdukacijuRequest) {
        Optional<Doktor> d = doktorRepository.findById(dodajEdukacijuRequest.getIdDoktora());
        if (!d.isPresent()) return new Response("Id doktora nije postojeći!", 400);
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

    public Response urediEdukaciju(HttpHeaders headers, UrediEdukacijuRequest urediEdukacijuRequest) {
        Optional<Edukacija> c = edukacijaRepository.findById(urediEdukacijuRequest.getId());
        if (!c.isPresent()) return new Response("Nepostojeći Id edukacije!", 400);

        if(!trenutniKorisnikSecurity.isTrenutniKorisnik(headers, c.get().getDoktor().getId()))
            throw new UnauthorizedException("Neovlašten pristup resursima!");

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

    public Response obrisiEdukaciju(HttpHeaders headers, Long id) {
        Optional<Edukacija> c = edukacijaRepository.findById(id);
        if (!c.isPresent()) return new Response("Nepostojeći Id edukacije!", 400);

        if(!trenutniKorisnikSecurity.isTrenutniKorisnik(headers, c.get().getDoktor().getId()))
            throw new UnauthorizedException("Neovlašten pristup resursima!");

        edukacijaRepository.delete(c.get());
        return new Response("Uspješno ste obrisali edukaciju!", 200);
    }

    public Response urediPodatkeDoktora(UrediPodatkeDoktoraRequest urediPodatkeDoktoraRequest) {
        Optional<Doktor> d = doktorRepository.findById(urediPodatkeDoktoraRequest.getIdDoktora());
        if (!d.isPresent()) return new Response("Id doktora nije postojeći!", 400);
        d.get().setTitula(urediPodatkeDoktoraRequest.getTitula());
        d.get().setBiografija(urediPodatkeDoktoraRequest.getBiografija());
        doktorRepository.save(d.get());
        return new Response("Uspješno ste uredili osnovne podatke doktora!", 200);
    }


    public String azurirajPodatke(List<KorisnikResponse> korisnikResponses) {
        List<Korisnik> korisnici = korisnikResponses
                .stream()
                .map(korisnikResponse -> new Korisnik(
                        korisnikResponse.getId(),
                        korisnikResponse.getIme(),
                        korisnikResponse.getPrezime(),
                        korisnikResponse.getDatumRodjenja(),
                        korisnikResponse.getAdresa(),
                        korisnikResponse.getBrojTelefona(),
                        korisnikResponse.getEmail()
                )).collect(Collectors.toList());
        for (Korisnik korisnik : korisnici) {
            Optional<Doktor> d = doktorRepository.findById(korisnik.getId());
            if (!d.isPresent()) {
                Doktor novi = new Doktor(korisnik.getId(), korisnik.getIme(),
                        korisnik.getPrezime(), korisnik.getDatumRodjenja(), korisnik.getAdresa(),
                        korisnik.getBrojTelefona(), korisnik.getEmail(), "", "");
                doktorRepository.save(novi);
            } else {
                d.get().setIme(korisnik.getIme());
                d.get().setPrezime(korisnik.getPrezime());
                d.get().setDatumRodjenja(korisnik.getDatumRodjenja());
                d.get().setAdresa(korisnik.getAdresa());
                d.get().setEmail(korisnik.getEmail());
                doktorRepository.save(d.get());
            }
        }
        return "Sinhronizacija završena. Podaci su ažurirani!";
    }
}

