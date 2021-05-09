package ba.unsa.etf.termini.controllers;

import ba.unsa.etf.termini.Requests.DodajTerminRequest;
import ba.unsa.etf.termini.Requests.UrediTerminRequest;
import ba.unsa.etf.termini.Responses.ListaTerminaResponse;
import ba.unsa.etf.termini.Responses.Response;
import ba.unsa.etf.termini.models.Doktor;
import ba.unsa.etf.termini.models.Pacijent;
import ba.unsa.etf.termini.models.PacijentKartonDoktor;
import ba.unsa.etf.termini.models.Termin;
import ba.unsa.etf.termini.repositories.DoktorRepository;
import ba.unsa.etf.termini.repositories.PacijentKartonDoktorRepository;
import ba.unsa.etf.termini.repositories.PacijentRepository;
import ba.unsa.etf.termini.repositories.TerminRepository;
import ba.unsa.etf.termini.services.TerminService;
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
public class TerminController {
    private TerminService terminService;
    private PacijentRepository pacijentRepository;
    private DoktorRepository doktorRepository;
    private PacijentKartonDoktorRepository pacijentKartonDoktorRepository;
    private TerminRepository terminRepository;

    private final Pacijent p1 = new Pacijent(
            "Samra",
            "Pusina",
            new Date(),
            "NekaAdresa",
            "061456321","samra@mail");
    private final Pacijent p2 = new Pacijent(
            "Esmina",
            "Radusic",
            new Date(),
            "NekaAdresa",
            "061456321",
            "esmina@mail");
    private final Doktor d1 = new Doktor(
            "Dzavid",
            "Brdar",
            new Date(),
            "NekaAdresa",
            "061456321",
            "dzavid@mail.com",
            "Doktor opce prakse");
    private final Doktor d2 = new Doktor(
            "Jusuf",
            "Delalic",
            new Date(),
            "NekaAdresa",
            "061456321",
            "jusuf@mail",
            "Doktor opce prakse");

    @GetMapping("/pohraniPocetneTermine")
    public
    String spasiListuTermina() {
        pacijentKartonDoktorRepository.deleteAllInBatch();
        pacijentKartonDoktorRepository.flush();
        terminRepository.deleteAllInBatch();
        terminRepository.flush();
        doktorRepository.deleteAllInBatch();
        doktorRepository.flush();
        pacijentRepository.deleteAllInBatch();
        pacijentRepository.flush();

        PacijentKartonDoktor pkd1=new PacijentKartonDoktor();
        PacijentKartonDoktor pkd2=new PacijentKartonDoktor();
        PacijentKartonDoktor pkd3=new PacijentKartonDoktor();
        PacijentKartonDoktor pkd4=new PacijentKartonDoktor();

        pkd1.setDoktor(d1);
        pkd2.setDoktor(d2);
        pkd3.setDoktor(d1);
        pkd4.setDoktor(d2);

        d1.getVezeSaPacijentima().add(pkd1);
        d2.getVezeSaPacijentima().add(pkd2);
        d1.getVezeSaPacijentima().add(pkd3);
        d2.getVezeSaPacijentima().add(pkd4);

        Termin termin1= new Termin(
                new Date(2021,5,25),
                "10:00",
                pkd1 );
        Termin termin2= new Termin(
                new Date(2021,5,25),
                "10:00",
                pkd2);
        Termin termin3= new Termin(
                new Date(2021,5,26),
                "11:00",
                pkd3);
        Termin termin4= new Termin(
                new Date(2021,5,26),
                "9:00",
                pkd4);

        pkd1.getTermini().add(termin1);
        pkd2.getTermini().add(termin2);
        pkd3.getTermini().add(termin3);
        pkd4.getTermini().add(termin4);

        doktorRepository.save(d1); doktorRepository.save(d2);
        pacijentRepository.save(p1); pacijentRepository.save(p2);

        List<Pacijent> pacijentList = pacijentRepository.findAll();

        List<PacijentKartonDoktor> pacijentKartonDoktorList = pacijentKartonDoktorRepository.findAll();

        pacijentKartonDoktorList.get(0).setPacijent(pacijentList.get(0));
        pacijentKartonDoktorList.get(1).setPacijent(pacijentList.get(1));
        pacijentKartonDoktorList.get(2).setPacijent(pacijentList.get(0));
        pacijentKartonDoktorList.get(3).setPacijent(pacijentList.get(1));

        pacijentList.get(0).getVezeSaDoktorima().add(pacijentKartonDoktorList.get(0));
        pacijentList.get(0).getVezeSaDoktorima().add(pacijentKartonDoktorList.get(1));
        pacijentList.get(1).getVezeSaDoktorima().add(pacijentKartonDoktorList.get(2));
        pacijentList.get(1).getVezeSaDoktorima().add(pacijentKartonDoktorList.get(3));

        pacijentRepository.save(pacijentList.get(0)); pacijentRepository.save(pacijentList.get(1));

        return "Spaseni pocetni termini!";
    }

    @PostMapping("/dodaj-termin")
    public ResponseEntity<Response> dodajTermin(@RequestBody DodajTerminRequest dodajTerminRequest){
        Response response = terminService.dodajTermin(dodajTerminRequest);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/termini/{id}")
    public ResponseEntity<Response> obrisiTermin(@PathVariable Long id) {
        Response response = terminService.obrisiTermin(id);
        return ResponseEntity.ok(response);
    }

//    @DeleteMapping("/obrisi-termin")
//    public ResponseEntity<Response> obrisiTermin(@RequestBody ObrisiTerminRequest obrisiTerminRequest) {
//        Response response = terminService.obrisiTerminPoAtributima(obrisiTerminRequest);
//        return ResponseEntity.ok(response);
//    }

    @PutMapping("/termini/{id}")
    public  ResponseEntity<Response> urediTermin(@RequestBody UrediTerminRequest urediTerminRequest, @PathVariable Long id){
        Response response=terminService.urediTermin(id, urediTerminRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/termini-pacijenta/{id}")
    public  ResponseEntity<ListaTerminaResponse> dajTerminePacijenta(@PathVariable Long id){
        ListaTerminaResponse response=terminService.dajTerminePacijenta(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/termini-doktora/{id}")
    public  ResponseEntity<ListaTerminaResponse> dajTermineDoktora(@PathVariable Long id){
        ListaTerminaResponse response=terminService.dajTermineDoktora(id);
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
