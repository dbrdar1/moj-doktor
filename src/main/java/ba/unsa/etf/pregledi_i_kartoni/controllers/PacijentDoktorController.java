package ba.unsa.etf.pregledi_i_kartoni.controllers;

import ba.unsa.etf.pregledi_i_kartoni.models.*;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajPacijentDoktorRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import ba.unsa.etf.pregledi_i_kartoni.services.PacijentDoktorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleNoSuchElementFoundException(
            ConstraintViolationException exception
    ) {
        String message="";
        List<String> messages = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toList());
        for (int i =0; i<messages.size();i++)
            if(i<messages.size()-1) message += messages.get(i)+ "; ";
            else message += messages.get(i);
        return new Response(message,400);
    }


}

