package ba.unsa.etf.doktordetalji.controllers;

import ba.unsa.etf.doktordetalji.exceptions.ResourceNotFoundException;
import ba.unsa.etf.doktordetalji.exceptions.UnauthorizedException;
import ba.unsa.etf.doktordetalji.models.Doktor;
import ba.unsa.etf.doktordetalji.requests.*;
import ba.unsa.etf.doktordetalji.responses.DoktorCVResponse;
import ba.unsa.etf.doktordetalji.responses.KorisnikResponse;
import ba.unsa.etf.doktordetalji.responses.Response;
import ba.unsa.etf.doktordetalji.security.TrenutniKorisnikSecurity;
import ba.unsa.etf.doktordetalji.services.DoktorService;
import ba.unsa.etf.doktordetalji.util.ErrorHandlingHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class DoktorController {

    private final DoktorService doktorService;

    @Autowired
    private final RestTemplate restTemplate;

    private final TrenutniKorisnikSecurity trenutniKorisnikSecurity;

    @PostMapping("/async")
    public ResponseEntity<Response> asyncKorisnici(@RequestBody AsyncRequest asyncRequest) throws ParseException {
        String poruka = doktorService.asyncKorisnici(asyncRequest);
        return ResponseEntity.ok(new Response(poruka));
    }

    @PostMapping("/sync")
    public ResponseEntity<Response> povuciPodatke(){
        String fooResourceUrl = "http://zuul-service/korisnici?uloga=doktor";
        ResponseEntity<KorisnikResponse[]> response = restTemplate.getForEntity(fooResourceUrl, KorisnikResponse[].class);
        KorisnikResponse[] doktori = response.getBody();
        String poruka = doktorService.azurirajPodatke(Arrays.asList(doktori));
        return ResponseEntity.ok(new Response(poruka));
    }

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
        DoktorCVResponse doktorCVResponse = doktorService.getDoktorCV(id);
        return ResponseEntity.ok(doktorCVResponse);
    }

    @PutMapping("/ocijeni-doktora")
    public ResponseEntity<Response> ocijeniDoktora(@RequestBody OcjenaRequest ocjenaRequest) {
        Response response = doktorService.ocjeni(ocjenaRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dodaj-certifikat")
    public ResponseEntity<Response> dodajCertifikat(@RequestHeader HttpHeaders headers, @RequestBody DodajCertifikatRequest dodajCertifikatRequest) {

        if(!trenutniKorisnikSecurity.isTrenutniKorisnik(headers, dodajCertifikatRequest.getIdDoktora()))
            throw new UnauthorizedException("Neovlašten pristup resursima!");

        Response response = doktorService.dodajCertifikat(dodajCertifikatRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/uredi-certifikat")
    public ResponseEntity<Response> urediCertifikat(@RequestHeader HttpHeaders headers, @RequestBody UrediCertifikatRequest urediCertifikatRequest) {

        Response response = doktorService.urediCertifikat(headers, urediCertifikatRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/obrisi-certifikat/{id}")
    public ResponseEntity<Response> obrisiCertifikat(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        Response response = doktorService.obrisiCertifikat(headers, id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dodaj-edukaciju")
    public ResponseEntity<Response> dodajEdukaciju(@RequestHeader HttpHeaders headers, @RequestBody DodajEdukacijuRequest dodajEdukacijuRequest) {

        if(!trenutniKorisnikSecurity.isTrenutniKorisnik(headers, dodajEdukacijuRequest.getIdDoktora()))
            throw new UnauthorizedException("Neovlašten pristup resursima!");

        Response response = doktorService.dodajEdukaciju(dodajEdukacijuRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/uredi-edukaciju")
    public ResponseEntity<Response> urediEdukaciju(@RequestHeader HttpHeaders headers, @RequestBody UrediEdukacijuRequest urediEdukacijuRequest) {
        Response response = doktorService.urediEdukaciju(headers, urediEdukacijuRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/obrisi-edukaciju/{id}")
    public ResponseEntity<Response> obrisiEdukaciju(@RequestHeader HttpHeaders headers, @PathVariable Long id) {
        Response response = doktorService.obrisiEdukaciju(headers, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/uredi-biografiju-titulu")
    public ResponseEntity<Response> urediPodatkeDoktora(@RequestHeader HttpHeaders headers, @RequestBody UrediPodatkeDoktoraRequest urediPodatkeDoktoraRequest) {

        if(!trenutniKorisnikSecurity.isTrenutniKorisnik(headers, urediPodatkeDoktoraRequest.getIdDoktora()))
            throw new UnauthorizedException("Neovlašten pristup resursima!");

        Response response = doktorService.urediPodatkeDoktora(urediPodatkeDoktoraRequest);
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

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response handleEntityUnauthorizedxception(UnauthorizedException exception) {
        return ErrorHandlingHelper.handleEntityUnauthorizedException(exception);
    }

}
