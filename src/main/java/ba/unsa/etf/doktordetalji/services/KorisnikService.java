package ba.unsa.etf.doktordetalji.services;

import ba.unsa.etf.doktordetalji.models.Certifikat;
import ba.unsa.etf.doktordetalji.models.Doktor;
import ba.unsa.etf.doktordetalji.models.Edukacija;
import ba.unsa.etf.doktordetalji.models.Korisnik;
import ba.unsa.etf.doktordetalji.repositories.*;
import ba.unsa.etf.doktordetalji.responses.KorisnikResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class KorisnikService {

    private final DoktorRepository doktorRepository;
    private final KorisnikRepository korisnikRepository;
    private final EdukacijaRepository edukacijaRepository;
    private final CertifikatRepository certifikatRepository;
    private final OcjenaRepository ocjenaRepository;


    public String inicijalizirajBazu() {

        edukacijaRepository.deleteAllInBatch();
        edukacijaRepository.flush();

        certifikatRepository.deleteAllInBatch();
        certifikatRepository.flush();

        doktorRepository.deleteAllInBatch();
        doktorRepository.flush();

        ocjenaRepository.deleteAllInBatch();
        ocjenaRepository.flush();


        Doktor d = new Doktor(
                1L,
                "Samra",
                "Pusina",
                new Date(1998, 5, 21),
                "NekaAdresa",
                "061456321",
                "spusina1@mail",
                "nekaTitula",
                "bachelor");


        Edukacija e1 = new Edukacija("ETF", "RI", "prvi", 2015, 2019, "Sarajevo", "BiH");
        Edukacija e2 = new Edukacija("ETF", "RI", "drugi", 2019, 2020, "Sarajevo", "BiH");

        Certifikat c1 = new Certifikat("EESTEC", "SSA", 2016);

        List<Edukacija> edukacijaList = new ArrayList<>();
        edukacijaList.add(e1);
        edukacijaList.add(e2);

        List<Certifikat> certifikatList = new ArrayList<>();
        certifikatList.add(c1);

        e1.setDoktor(d);
        e2.setDoktor(d);
        c1.setDoktor(d);

        d.setEdukacije(edukacijaList);
        d.setCertifikati(certifikatList);

        doktorRepository.save(d);

        return "Inicijalizacija baze zavrsena!";
    }

    public List<KorisnikResponse> getKorisnici() {
        List<KorisnikResponse> korisnikResponses = new ArrayList<>();
        List<Korisnik> korisnikList = korisnikRepository.findAll();
        korisnikResponses = korisnikList.stream()
                .map(korisnik -> new KorisnikResponse(korisnik.getId(), korisnik.getIme(), korisnik.getPrezime(), korisnik.getDatumRodjenja(), korisnik.getAdresa(), korisnik.getBrojTelefona(), korisnik.getEmail()))
                .collect(Collectors.toList());
        return korisnikResponses;
    }
}
