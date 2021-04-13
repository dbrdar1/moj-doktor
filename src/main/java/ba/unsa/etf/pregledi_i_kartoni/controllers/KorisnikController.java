package ba.unsa.etf.pregledi_i_kartoni.controllers;

import ba.unsa.etf.pregledi_i_kartoni.models.Doktor;
import ba.unsa.etf.pregledi_i_kartoni.models.Korisnik;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajDoktoraRequest;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajKorisnikaRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.KorisnikResponse;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import ba.unsa.etf.pregledi_i_kartoni.services.DoktorService;
import ba.unsa.etf.pregledi_i_kartoni.services.KorisnikService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ba.unsa.etf.grpc.ActionRequest;
import ba.unsa.etf.grpc.ActionResponse;
import ba.unsa.etf.grpc.SystemEventsServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


@AllArgsConstructor
@RestController
public class KorisnikController {

    private final KorisnikService korisnikService;

    // prikaz jednog korisnika na osnovu id
    @GetMapping("/korisnik/{idKorisnika}")
    public ResponseEntity<KorisnikResponse> dajKorisnika(@PathVariable(value = "idKorisnika") Long idKorisnika){
        
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8866)
                .usePlaintext()
                .build();

        SystemEventsServiceGrpc.SystemEventsServiceBlockingStub stub =
                SystemEventsServiceGrpc.newBlockingStub(channel);

        ActionResponse actionResponse = stub.registrujAkciju(ActionRequest.newBuilder()
            .setResurs("neki resurs")
            .build());

        System.out.println("Response received from server:\n" + actionResponse);

        channel.shutdown();

        
        
        KorisnikResponse trazeniKorisnik = korisnikService.dajKorisnikaNaOsnovuId(idKorisnika);
        return ResponseEntity.ok(trazeniKorisnik);
    }

    // prikaz svih korisnika
    @GetMapping("/svi-korisnici")
    public ResponseEntity<List<KorisnikResponse>> dajSveKorisnike(){
        List<KorisnikResponse> sviKorisnici = korisnikService.dajSveKorisnike();
        return ResponseEntity.ok(sviKorisnici);
    }

    // pohrana korisnika
    @PostMapping("/dodaj-korisnika")
    public ResponseEntity<Response> dodajKorisnika(@RequestBody DodajKorisnikaRequest dodajKorisnikaRequest) {
        Response response = korisnikService.dodajKorisnika(dodajKorisnikaRequest);
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

