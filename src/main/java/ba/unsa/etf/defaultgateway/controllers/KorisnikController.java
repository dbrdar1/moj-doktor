package ba.unsa.etf.defaultgateway.controllers;

import ba.unsa.etf.defaultgateway.exceptions.ResourceNotFoundException;
import ba.unsa.etf.defaultgateway.models.Korisnik;
import ba.unsa.etf.defaultgateway.requests.*;
import ba.unsa.etf.defaultgateway.responses.KorisnikResponse;
import ba.unsa.etf.defaultgateway.responses.LoginResponse;
import ba.unsa.etf.defaultgateway.responses.Response;

import ba.unsa.etf.defaultgateway.security.CurrentUser;
import ba.unsa.etf.defaultgateway.security.UserPrincipal;
import ba.unsa.etf.defaultgateway.services.KorisnikService;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class KorisnikController {

    private final KorisnikService korisnikService;

    @GetMapping("/")
    public ResponseEntity<Response> pocetna() {
        String poruka = korisnikService.pripremiUloge();
        return ResponseEntity.ok(new Response(poruka));
    }

    @GetMapping("/inicijalizacija-baze")
    public ResponseEntity<Response> inicijalizacija() {
        String poruka = korisnikService.inicijalizirajBazu();
        return ResponseEntity.ok(new Response(poruka));
    }

    @PostMapping("/registracija")
    public ResponseEntity<Response> registracijaKorisnika(@Valid @RequestBody RegistracijaRequest registracijaRequest){
        String odgovor = korisnikService.registrujKorisnika(registracijaRequest);
        return ResponseEntity.ok(new Response(odgovor));
    }

    @PostMapping("/prijava")
    public ResponseEntity<LoginResponse> autentificirajKorisnika(@Valid @RequestBody LoginRequest loginRequest) {
        String jwt = korisnikService.autentificirajKorisnika(loginRequest);
        return ResponseEntity.ok(new LoginResponse(jwt));
    }

    @GetMapping("/profil")
    public ResponseEntity<Korisnik> getKorisnickiProfil(@CurrentUser UserPrincipal userPrincipal) {
        Korisnik korisnik = korisnikService.getKorisnikByKorisnickoIme(userPrincipal.getUsername());
        return ResponseEntity.ok(korisnik);
    }

    @PostMapping("/reset-token")
    public ResponseEntity<Response> getResetToken(@Valid @RequestBody GetResetTokenRequest getResetTokenRequest) throws MessagingException, IOException, TemplateException, javax.mail.MessagingException {
        Response response = korisnikService.generirajResetToken(getResetTokenRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verifikacijski-podaci")
    public ResponseEntity<Response> verificiraj(@Valid @RequestBody VerificirajPodatkeRequest verificirajPodatkeRequest) throws MessagingException {
        Response response = korisnikService.verificirajPodatke(verificirajPodatkeRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/uredjivanje_lozinke")
    public ResponseEntity<Response> spasiLozinku(@Valid @RequestBody SpasiLozinkuRequest spasiLozinkuRequest) throws MessagingException {
        Response response = korisnikService.promijeniLozinku(spasiLozinkuRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/korisnici")
    public List<KorisnikResponse> getKorisnici(@RequestParam(required = false) String uloga) {
        if (uloga == null) uloga = "";
        return korisnikService.getKorisnici(uloga);
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