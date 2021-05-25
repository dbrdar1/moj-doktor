package ba.unsa.etf.pregledi_i_kartoni.controllers;

import ba.unsa.etf.pregledi_i_kartoni.exceptions.ResourceNotFoundException;
import ba.unsa.etf.pregledi_i_kartoni.models.Doktor;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajDoktoraRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.DoktorResponse;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import ba.unsa.etf.pregledi_i_kartoni.services.DoktorService;
import ba.unsa.etf.pregledi_i_kartoni.util.ErrorHandlingHelper;
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
    @GetMapping("/doktori/{idDoktora}")
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
    public Response handleConstraintViolationException(ConstraintViolationException exception) {
        return ErrorHandlingHelper.handleConstraintViolationException(exception);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleEntityNotFoundException(ResourceNotFoundException exception) {
        return ErrorHandlingHelper.handleEntityNotFoundException(exception);
    }

}

