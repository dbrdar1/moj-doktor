package ba.unsa.etf.doktordetalji.controllers;

import ba.unsa.etf.doktordetalji.responses.Response;
import ba.unsa.etf.doktordetalji.services.KorisnikService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@AllArgsConstructor
@RestController
public class KorisnikController {

    private final KorisnikService korisnikService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/inicijalizacija-baze")
    public ResponseEntity<Response> inicijalizacija(){
        String poruka = korisnikService.inicijalizirajBazu();
        return ResponseEntity.ok(new Response(poruka));
    }

    @PostMapping("/pokupi-podatke-o-korisnicima")
    public ResponseEntity<String> postKorisnici(){

        String fooResourceUrl = "http://user-management/getKorisnici";
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
        return response;
    }

}
