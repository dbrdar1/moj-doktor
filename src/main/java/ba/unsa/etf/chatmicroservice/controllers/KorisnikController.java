package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.models.Korisnik;
import ba.unsa.etf.chatmicroservice.services.KorisnikService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class KorisnikController {

    private final KorisnikService korisnikService;

    @GetMapping("/inicijalizacijaBaze")
    public @ResponseBody String inicijalizacija(){
        return korisnikService.inicijalizirajBazu();
    }
}

