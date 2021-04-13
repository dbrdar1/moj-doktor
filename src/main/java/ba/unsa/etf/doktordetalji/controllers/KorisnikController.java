package ba.unsa.etf.doktordetalji.controllers;

import ba.unsa.etf.doktordetalji.responses.DoktorCVResponse;
import ba.unsa.etf.doktordetalji.responses.KorisnikResponse;
import ba.unsa.etf.doktordetalji.responses.Response;
import ba.unsa.etf.doktordetalji.services.DoktorService;
import ba.unsa.etf.doktordetalji.services.KorisnikService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@AllArgsConstructor
@RestController
public class KorisnikController {

    private final KorisnikService korisnikService;
    private final DoktorService doktorService;

    @Autowired
    private final RestTemplate restTemplate;

    @GetMapping("/inicijalizacija-baze")
    public ResponseEntity<Response> inicijalizacija() {
        String poruka = korisnikService.inicijalizirajBazu();
        return ResponseEntity.ok(new Response(poruka));
    }

    @PutMapping("/pokupi-podatke-o-doktorima")
    public ResponseEntity<Response> postDoktori() {
        String fooResourceUrl = "http://user-management/getDoktori";
        ResponseEntity<KorisnikResponse[]> response = restTemplate.getForEntity(fooResourceUrl, KorisnikResponse[].class);
        KorisnikResponse[] doktori = response.getBody();
        String poruka = doktorService.azurirajPodatke(Arrays.asList(doktori));
        return ResponseEntity.ok(new Response(poruka));
    }

    @GetMapping("/korisnici")
    public List<KorisnikResponse> getKorisnici() {
        return korisnikService.getKorisnici();
    }

}
