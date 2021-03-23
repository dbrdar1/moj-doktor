package ba.unsa.etf.doktordetalji.controllers;

import ba.unsa.etf.doktordetalji.models.Doktor;
import ba.unsa.etf.doktordetalji.requests.*;
import ba.unsa.etf.doktordetalji.responses.DoktorCVResponse;
import ba.unsa.etf.doktordetalji.responses.Response;
import ba.unsa.etf.doktordetalji.services.DoktorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                ime,prezime,titula,ocjena
        );
        return doktorService.getDoktori(filterRequest);
    }

    @GetMapping("/doktori/{id}")
    public ResponseEntity<DoktorCVResponse> getDoktor(@PathVariable Long id){
        System.out.println(id);
        DoktorCVResponse doktorCVResponse = doktorService.getDoktorCV(id);
        return ResponseEntity.ok(doktorCVResponse);
    }

    @PutMapping("/ocijeni-doktora")
    public ResponseEntity<Response> ocijeniDoktora(@RequestBody OcjenaRequest ocjenaRequest){
        Response response = doktorService.ocjeni(ocjenaRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dodaj-certifikat")
    public ResponseEntity<Response> dodajCertifikat(@RequestBody DodajCertifikatRequest dodajCertifikatRequest){
        Response response = doktorService.dodajCertifikat(dodajCertifikatRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/uredi-certifikat")
    public ResponseEntity<Response> urediCertifikat(@RequestBody UrediCertifikatRequest urediCertifikatRequest){
        Response response = doktorService.urediCertifikat(urediCertifikatRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/obrisi-certifikat/{id}")
    public ResponseEntity<Response> obrisiCertifikat(@PathVariable Long id){
        Response response = doktorService.obrisiCertifikat(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dodaj-edukaciju")
    public ResponseEntity<Response> dodajEdukaciju(@RequestBody DodajEdukacijuRequest dodajEdukacijuRequest){
        Response response = doktorService.dodajEdukaciju(dodajEdukacijuRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/uredi-edukaciju")
    public ResponseEntity<Response> urediEdukaciju(@RequestBody UrediEdukacijuRequest urediEdukacijuRequest){
        Response response = doktorService.urediEdukaciju(urediEdukacijuRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/obrisi-edukaciju/{id}")
    public ResponseEntity<Response> obrisiEdukaciju(@PathVariable Long id){
        Response response = doktorService.obrisiEdukaciju(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/uredi-osnovne-podatke-doktora")
    public ResponseEntity<Response> urediPodatkeDoktora(@RequestBody UrediPodatkeDoktoraRequest urediPodatkeDoktoraRequest){
        Response response = doktorService.urediPodatkeDoktora(urediPodatkeDoktoraRequest);
        return ResponseEntity.ok(response);
    }
}
