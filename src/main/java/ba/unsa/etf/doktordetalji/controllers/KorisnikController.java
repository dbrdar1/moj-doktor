package ba.unsa.etf.doktordetalji.controllers;

import ba.unsa.etf.doktordetalji.responses.Response;
import ba.unsa.etf.doktordetalji.services.KorisnikService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class KorisnikController {

    private final KorisnikService korisnikService;

    @GetMapping("/inicijalizacija-baze")
    public ResponseEntity<Response> inicijalizacija(){
        String poruka = korisnikService.inicijalizirajBazu();
        return ResponseEntity.ok(new Response(poruka));
    }

}
