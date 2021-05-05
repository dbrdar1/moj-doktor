package ba.unsa.etf.doktordetalji.controllers;

import ba.unsa.etf.doktordetalji.exceptions.ResourceNotFoundException;
import ba.unsa.etf.doktordetalji.models.Doktor;
import ba.unsa.etf.doktordetalji.requests.*;
import ba.unsa.etf.doktordetalji.responses.DoktorCVResponse;
import ba.unsa.etf.doktordetalji.responses.Response;
import ba.unsa.etf.doktordetalji.services.DoktorService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@RestController
public class DoktorController {

    private final DoktorService doktorService;

    @GetMapping("/doktori")
    public List<Doktor> getProducts(
            @RequestParam(required = false) String ime,
            @RequestParam(required = false) String prezime,
            @RequestParam(required = false) String titula,
            @RequestParam(required = false) Integer ocjena
    ) {
        FilterRequest filterRequest = new FilterRequest(
                ime, prezime, titula, ocjena
        );

        return doktorService.getDoktori(filterRequest);
    }

    @GetMapping("/doktori/{id}")
    public ResponseEntity<DoktorCVResponse> getDoktor(@PathVariable Long id) {
        System.out.println(id);
        DoktorCVResponse doktorCVResponse = doktorService.getDoktorCV(id);
        return ResponseEntity.ok(doktorCVResponse);
    }

    @PutMapping("/ocijeni-doktora")
    public ResponseEntity<Response> ocijeniDoktora(@RequestBody OcjenaRequest ocjenaRequest) {
        Response response = doktorService.ocjeni(ocjenaRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dodaj-certifikat")
    public ResponseEntity<Response> dodajCertifikat(@RequestBody DodajCertifikatRequest dodajCertifikatRequest) {
        Response response = doktorService.dodajCertifikat(dodajCertifikatRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/uredi-certifikat")
    public ResponseEntity<Response> urediCertifikat(@RequestBody UrediCertifikatRequest urediCertifikatRequest) {
        Response response = doktorService.urediCertifikat(urediCertifikatRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/obrisi-certifikat/{id}")
    public ResponseEntity<Response> obrisiCertifikat(@PathVariable Long id) {
        Response response = doktorService.obrisiCertifikat(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dodaj-edukaciju")
    public ResponseEntity<Response> dodajEdukaciju(@RequestBody DodajEdukacijuRequest dodajEdukacijuRequest) {
        Response response = doktorService.dodajEdukaciju(dodajEdukacijuRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/uredi-edukaciju")
    public ResponseEntity<Response> urediEdukaciju(@RequestBody UrediEdukacijuRequest urediEdukacijuRequest) {
        Response response = doktorService.urediEdukaciju(urediEdukacijuRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/obrisi-edukaciju/{id}")
    public ResponseEntity<Response> obrisiEdukaciju(@PathVariable Long id) {
        Response response = doktorService.obrisiEdukaciju(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/uredi-osnovne-podatke-doktora")
    public ResponseEntity<Response> urediPodatkeDoktora(@RequestBody UrediPodatkeDoktoraRequest urediPodatkeDoktoraRequest) {
        Response response = doktorService.urediPodatkeDoktora(urediPodatkeDoktoraRequest);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleNoSuchElementFoundException(ConstraintViolationException exception) {
        StringBuilder message = new StringBuilder();
        List<String> messages = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toList());
        for (int i = 0; i < messages.size(); i++)
            if (i < messages.size() - 1) message.append(messages.get(i)).append("; ");
            else message.append(messages.get(i));
        return new Response(message.toString(), 400);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public final Response handleException(Exception e) {
        if (e instanceof ResourceNotFoundException) {
            ResourceNotFoundException exception = (ResourceNotFoundException) e;
            String poruka = exception.getMessage();
            return new Response(poruka, 400);
        }
        return null;
    }

}
