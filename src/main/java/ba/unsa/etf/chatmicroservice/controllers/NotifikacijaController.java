package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.models.Korisnik;
import ba.unsa.etf.chatmicroservice.models.Notifikacija;
import ba.unsa.etf.chatmicroservice.services.NotifikacijaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class NotifikacijaController {

    private final NotifikacijaService notifikacijaService;

    private final List<Notifikacija> notifikacije;
    private final Korisnik korisnik1 = new Korisnik(
            "Samra",
            "Pusina",
            new Date(),
            "NekaAdresa",
            "samra@mail",
            "061456321");
    private final Korisnik korisnik2 = new Korisnik(
            "Esmina",
            "Radusic",
            new Date(),
            "NekaAdresa",
            "esmina@mail",
            "061456321");

    private final Notifikacija notifikacija1 = new Notifikacija("nesto", "ness", korisnik1);
    private final Notifikacija notifikacija2 = new Notifikacija("nesto1", "ness2", korisnik2);

    @GetMapping("/pohraniNotifikacije")
    public @ResponseBody String spasiListuNotifikacija() {
        notifikacije.add(notifikacija1);
        notifikacije.add(notifikacija2);
        return notifikacijaService.spasiNotifikacije(notifikacije);
    }
}

