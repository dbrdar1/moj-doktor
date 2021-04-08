package ba.unsa.etf.pregledi_i_kartoni.controllers;

import ba.unsa.etf.pregledi_i_kartoni.models.Doktor;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajDoktoraRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.DoktorResponse;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import ba.unsa.etf.pregledi_i_kartoni.services.DoktorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class DoktorController {


    private final DoktorService doktorService;


    // prikaz jednog doktora na osnovu id
    @GetMapping("/doktor/{idDoktora}")
    public ResponseEntity<DoktorResponse> dajDoktora(@PathVariable(value = "idDoktora") Long idDoktora){
        DoktorResponse trazeniDoktor = doktorService.dajDoktoraNaOsnovuId(idDoktora);
        return ResponseEntity.ok(trazeniDoktor);
    }

    // prikaz svih doktora
    @GetMapping("/svi-doktori")
    public ResponseEntity<List<DoktorResponse>> dajSveDoktore(){
        List<DoktorResponse> trazeniDoktori = doktorService.dajSveDoktore();
        return ResponseEntity.ok(trazeniDoktori);
    }

    // pohrana doktora
    @PostMapping("/dodaj-doktora")
    public ResponseEntity<Response> dodajDoktora(@RequestBody DodajDoktoraRequest dodajDoktoraRequest) {
        Response response = doktorService.dodajDoktora(dodajDoktoraRequest);
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

