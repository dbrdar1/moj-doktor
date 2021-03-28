package ba.unsa.etf.pregledi_i_kartoni.controllers;

import ba.unsa.etf.pregledi_i_kartoni.models.*;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajPacijentDoktorRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import ba.unsa.etf.pregledi_i_kartoni.services.PacijentDoktorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class PacijentDoktorController {

    private final PacijentDoktorService pacijentDoktorService;

    private final List<PacijentDoktor> pacijentiDoktori;


    private final Doktor doktor1 = new Doktor("ImeDoktora1", "PrezimeDoktora1", new Date(), "AdresaDoktora1", "061-123-123", "nekimaildoktora1@gmail.com");
    private final Pacijent pacijent1 = new Pacijent( "Ime1", "Prezime1", new Date(), "Adresa1", "061-456-456", "nekimailp1@gmail.com",
            "zenski", 175.2, 68.3, "0+", "/", "/");

    private final Doktor doktor2 = new Doktor("ImeDoktora2", "PrezimeDoktora2", new Date(), "AdresaDoktora2", "061-723-723", "nekimaildoktora2@gmail.com");
    private final Pacijent pacijent2 = new Pacijent( "Ime2", "Prezime2", new Date(), "Adresa2", "061-323-323", "nekimailp2@gmail.com",
            "zenski", 165.4, 56.3, "A-", "/", "/");




    @PostMapping("/dodaj-vezu-pacijent-doktor")
    public ResponseEntity<Response> spasiVezuPacijentDoktor(@RequestBody DodajPacijentDoktorRequest dodajPacijentDoktorRequest) {

        Response response = pacijentDoktorService.spasiVezuPacijentDoktor(dodajPacijentDoktorRequest.getPacijentId(),
                                                                          dodajPacijentDoktorRequest.getDoktorId()
                                                                         );
        return ResponseEntity.ok(response);

        }



}

