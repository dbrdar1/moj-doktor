package ba.unsa.etf.doktordetalji.services;

import ba.unsa.etf.doktordetalji.models.Certifikat;
import ba.unsa.etf.doktordetalji.models.Doktor;
import ba.unsa.etf.doktordetalji.models.Edukacija;
import ba.unsa.etf.doktordetalji.models.Pacijent;
import ba.unsa.etf.doktordetalji.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class KorisnikService {

    private final DoktorRepository doktorRepository;
    private  final PacijentRepository pacijentRepository;

    public String inicijalizirajBazu() {

        Doktor d = new Doktor(
                "Samra",
                "Pusina",
                new Date(),
                "NekaAdresa",
                "061456321",
                "spusina1@mail",
                "nekaTitula",
                "bachelor",
                4);

        Pacijent p = new Pacijent(
                "Esmina",
                "Radusic",
                new Date(),
                "NekaAdresa",
                "061456322",
                "eradusic1@mail");

        Edukacija e1 = new Edukacija("ETF", "RI", "1", 2017, 2020, "Sarajevo", "BiH");
        Edukacija e2 = new Edukacija("ETF", "RI", "2", 2020, 2022, "Sarajevo", "BiH");

        Certifikat c1 = new Certifikat("EESTEC", "SSA", new Date());

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
        pacijentRepository.save(p);

        return "Inicijalizacija baze završena!";
    }
}
