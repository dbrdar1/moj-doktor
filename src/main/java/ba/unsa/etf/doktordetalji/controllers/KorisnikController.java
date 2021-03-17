package ba.unsa.etf.doktordetalji.controllers;

import ba.unsa.etf.doktordetalji.services.DoktorService;
import ba.unsa.etf.doktordetalji.services.KorisnikService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class KorisnikController {

    private final KorisnikService korisnikService;

    @GetMapping("/inicijalizacijaBaze")
    public @ResponseBody
    String inicijalizacija(){

        return  korisnikService.inicijalizirajBazu();
    }
}
