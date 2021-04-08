package ba.unsa.etf.pregledi_i_kartoni.controllers;

import ba.unsa.etf.pregledi_i_kartoni.models.*;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajTerminRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import ba.unsa.etf.pregledi_i_kartoni.responses.TerminResponse;
import ba.unsa.etf.pregledi_i_kartoni.services.DoktorService;
import ba.unsa.etf.pregledi_i_kartoni.services.PacijentDoktorService;
import ba.unsa.etf.pregledi_i_kartoni.services.PacijentService;
import ba.unsa.etf.pregledi_i_kartoni.services.TerminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class TerminController {

    private final TerminService terminService;



    // prikaz svih termina
    @GetMapping("/svi-termini")
    public ResponseEntity<List<TerminResponse>> dajSveTermine(){
        List<TerminResponse> trazeniTermini = terminService.dajSveTermine();
        return ResponseEntity.ok(trazeniTermini);
    }

    // prikaz termina na osnovu id
    @GetMapping("/termin/{idTermina}")
        public ResponseEntity<TerminResponse> dajTermin(@PathVariable(value = "idTermina") Long idTermina){
        TerminResponse trazeniTermin = terminService.dajTerminNaOsnovuId(idTermina);
        return ResponseEntity.ok(trazeniTermin);
    }


    // pohrana termina
    @PostMapping("/dodaj-termin")
    public ResponseEntity<Response> dodajTermin(@RequestBody DodajTerminRequest dodajTerminRequest) {
        Response response = terminService.dodajTermin(dodajTerminRequest);
        return ResponseEntity.ok(response);
    }

    // brisanje termina
    @DeleteMapping("/termin/{idTermina}")
    public ResponseEntity<Response> obrisiTermin(@PathVariable(value = "idTermina") Long idTermina) {
        Response response = terminService.obrisiTermin(idTermina);
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



    @GetMapping("/termini-pacijenta/{idPacijenta}")
    public ResponseEntity<String> dajTerminePacijenta(@PathVariable(value = "idPacijenta") Long idPacijenta) {

        String response = terminService.dajTerminePacijenta(idPacijenta);
        return ResponseEntity.ok(response);

    }

}

