package ba.unsa.etf.pregledi_i_kartoni.services;

import ba.unsa.etf.pregledi_i_kartoni.exceptions.ResourceNotFoundException;
import ba.unsa.etf.pregledi_i_kartoni.exceptions.UnauthorizedException;
import ba.unsa.etf.pregledi_i_kartoni.models.Doktor;
import ba.unsa.etf.pregledi_i_kartoni.models.Pacijent;
import ba.unsa.etf.pregledi_i_kartoni.repositories.DoktorRepository;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajDoktoraRequest;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajPacijentaRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.DoktorResponse;
import ba.unsa.etf.pregledi_i_kartoni.responses.PacijentResponse;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import ba.unsa.etf.pregledi_i_kartoni.security.TrenutniKorisnikSecurity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DoktorService {

    private final DoktorRepository doktorRepository;
    private final TrenutniKorisnikSecurity trenutniKorisnikSecurity;


    public Response dodajDoktora(DodajDoktoraRequest dodajDoktoraRequest) {
        Doktor noviDoktor = new Doktor(
                dodajDoktoraRequest.getIme(), dodajDoktoraRequest.getPrezime(), dodajDoktoraRequest.getDatumRodjenja(),
                dodajDoktoraRequest.getAdresa(), dodajDoktoraRequest.getBrojTelefona(), dodajDoktoraRequest.getEmail()
        );
        doktorRepository.save(noviDoktor);
        return new Response("Uspješno ste dodali doktora!", 200);
    }


    public DoktorResponse dajDoktoraNaOsnovuId(Long idDoktora) {

        String errorMessageDoktor = String.format("Ne postoji doktor sa id = '%d'!", idDoktora);
        Doktor trazeniDoktor = doktorRepository.findById(idDoktora).orElseThrow(() -> new ResourceNotFoundException(errorMessageDoktor));
        return new DoktorResponse(trazeniDoktor.getId(), trazeniDoktor.getIme(), trazeniDoktor.getPrezime(),
                trazeniDoktor.getDatumRodjenja(), trazeniDoktor.getAdresa(), trazeniDoktor.getBrojTelefona(),
                trazeniDoktor.getEmail()
        );

    }


    // svi doktori
    public List<DoktorResponse> dajSveDoktore() {
        List<Doktor> doktori = doktorRepository.findAll();
        List<DoktorResponse> listaDoktorResponse = new ArrayList<>();
        for (Doktor doktor : doktori) {
            listaDoktorResponse.add(new DoktorResponse(doktor.getId(), doktor.getIme(), doktor.getPrezime(),
                            doktor.getDatumRodjenja(), doktor.getAdresa(), doktor.getBrojTelefona(),
                            doktor.getEmail()
                    )
            );
        }

        return listaDoktorResponse;
    }

    // svi doktori nekog pacijenta
    public List<DoktorResponse> dajDoktorePacijenta(HttpHeaders headers, Long idPacijent) {

        if(!trenutniKorisnikSecurity.isTrenutniKorisnik(headers, idPacijent)) {
            throw new UnauthorizedException("Neovlašten pristup resursima!");
        }

        List<Doktor> trazeniDoktori = doktorRepository.findDoktoriPacijenta(idPacijent).get();
        List<DoktorResponse> listaDoktorResponse = new ArrayList<>();

        for (Doktor d : trazeniDoktori) {
            listaDoktorResponse.add(new DoktorResponse(d.getId(), d.getIme(), d.getPrezime(),
                            d.getDatumRodjenja(), d.getAdresa(), d.getBrojTelefona(),
                            d.getEmail()
                    )
            );

        }

        return listaDoktorResponse;

    }
}
