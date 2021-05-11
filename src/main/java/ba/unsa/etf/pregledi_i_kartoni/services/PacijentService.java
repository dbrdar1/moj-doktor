package ba.unsa.etf.pregledi_i_kartoni.services;

import ba.unsa.etf.pregledi_i_kartoni.exceptions.ResourceNotFoundException;
import ba.unsa.etf.pregledi_i_kartoni.models.Korisnik;
import ba.unsa.etf.pregledi_i_kartoni.models.Pacijent;
import ba.unsa.etf.pregledi_i_kartoni.repositories.KorisnikRepository;
import ba.unsa.etf.pregledi_i_kartoni.repositories.PacijentRepository;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajPacijentaRequest;
import ba.unsa.etf.pregledi_i_kartoni.requests.UrediKartonRequest;
import ba.unsa.etf.pregledi_i_kartoni.responses.KartonResponse;
import ba.unsa.etf.pregledi_i_kartoni.responses.PacijentResponse;
import ba.unsa.etf.pregledi_i_kartoni.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PacijentService {

    private final PacijentRepository pacijentRepository;

    public Response dodajPacijenta(DodajPacijentaRequest dodajPacijentaRequest) {

        Pacijent noviPacijent = new Pacijent(
                dodajPacijentaRequest.getIme(), dodajPacijentaRequest.getPrezime(), dodajPacijentaRequest.getDatumRodjenja(),
                dodajPacijentaRequest.getAdresa(), dodajPacijentaRequest.getBrojTelefona(), dodajPacijentaRequest.getEmail(),
                dodajPacijentaRequest.getSpol(), dodajPacijentaRequest.getVisina(), dodajPacijentaRequest.getTezina(),
                dodajPacijentaRequest.getKrvnaGrupa(), dodajPacijentaRequest.getHronicneBolesti(), dodajPacijentaRequest.getHronicnaTerapija()
        );
        pacijentRepository.save(noviPacijent);
        return new Response("Uspješno ste dodali pacijenta!", 200);
    }


    // pacijent kao korisnik
    public PacijentResponse dajPacijentaNaOsnovuId(Long id) {
        String errorMessage = String.format("Ne postoji pacijent sa id = '%d'", id);
        Pacijent trazeniPacijent = pacijentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(errorMessage));
        return new PacijentResponse(trazeniPacijent.getId(), trazeniPacijent.getIme(), trazeniPacijent.getPrezime(),
                                    trazeniPacijent.getDatumRodjenja(), trazeniPacijent.getAdresa(), trazeniPacijent.getBrojTelefona(),
                                    trazeniPacijent.getEmail()
                                   );
    }


    // svi pacijenti kao korisnici
    public List<PacijentResponse> dajSvePacijente() {
        List<Pacijent> pacijenti = pacijentRepository.findAll();
        List<PacijentResponse> listaPacijentResponse = new ArrayList<>();
        for (Pacijent pacijent : pacijenti) {
            listaPacijentResponse.add(new PacijentResponse(pacijent.getId(), pacijent.getIme(), pacijent.getPrezime(),
                                                           pacijent.getDatumRodjenja(), pacijent.getAdresa(), pacijent.getBrojTelefona(),
                                                           pacijent.getEmail()
                                                          )
                                     );
        }

        return listaPacijentResponse;
    }


    // pacijent kao karton pacijenta
    public KartonResponse dajKartonNaOsnovuId(Long id) {
        String errorMessage = String.format("Ne postoji pacijent sa id = '%d'", id);
        Pacijent trazeniPacijent = pacijentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(errorMessage));
        return new KartonResponse(trazeniPacijent.getId(), trazeniPacijent.getIme(), trazeniPacijent.getPrezime(),
                trazeniPacijent.getDatumRodjenja(), trazeniPacijent.getAdresa(), trazeniPacijent.getBrojTelefona(),
                trazeniPacijent.getEmail(), trazeniPacijent.getSpol(), trazeniPacijent.getVisina(),
                trazeniPacijent.getTezina(), trazeniPacijent.getKrvnaGrupa(), trazeniPacijent.getHronicneBolesti(),
                trazeniPacijent.getHronicnaTerapija()
        );
    }


    // svi kartoni pacijenata
    public List<KartonResponse> dajSveKartone() {
        List<Pacijent> pacijenti = pacijentRepository.findAll();
        List<KartonResponse> listaKartonResponse = new ArrayList<>();
        for (Pacijent pacijent : pacijenti) {
            listaKartonResponse.add(new KartonResponse(pacijent.getId(), pacijent.getIme(), pacijent.getPrezime(),
                            pacijent.getDatumRodjenja(), pacijent.getAdresa(), pacijent.getBrojTelefona(),
                            pacijent.getEmail(), pacijent.getSpol(), pacijent.getVisina(),
                            pacijent.getTezina(), pacijent.getKrvnaGrupa(), pacijent.getHronicneBolesti(),
                            pacijent.getHronicnaTerapija()
                    )
            );
        }

        return listaKartonResponse;
    }


    public List<KartonResponse> filtrirajKartone(String ime, String prezime, String spol,
                                                 String krvnaGrupa, String hronicneBolesti, String hronicnaTerapija) {

        List<Pacijent> trazeniPacijenti = pacijentRepository.findByQueryKarton(ime, prezime, spol, krvnaGrupa, hronicneBolesti, hronicnaTerapija).get();
        List<KartonResponse> listaKartonResponse = new ArrayList<>();
        for (Pacijent pacijent : trazeniPacijenti) {
            listaKartonResponse.add(new KartonResponse(pacijent.getId(), pacijent.getIme(), pacijent.getPrezime(),
                            pacijent.getDatumRodjenja(), pacijent.getAdresa(), pacijent.getBrojTelefona(),
                            pacijent.getEmail(), pacijent.getSpol(), pacijent.getVisina(),
                            pacijent.getTezina(), pacijent.getKrvnaGrupa(), pacijent.getHronicneBolesti(),
                            pacijent.getHronicnaTerapija()
                    )
            );

        }

        return listaKartonResponse;

    }

    public List<PacijentResponse> filtrirajPacijente(String ime, String prezime) {

        List<Pacijent> trazeniPacijenti = pacijentRepository.findByQueryPacijent(ime, prezime).get();
        List<PacijentResponse> listaPacijentResponse = new ArrayList<>();
        for (Pacijent pacijent : trazeniPacijenti) {
            listaPacijentResponse.add(new PacijentResponse(pacijent.getId(), pacijent.getIme(), pacijent.getPrezime(),
                            pacijent.getDatumRodjenja(), pacijent.getAdresa(), pacijent.getBrojTelefona(),
                            pacijent.getEmail()
                    )
            );

        }

        return listaPacijentResponse;

    }



    public Response urediKarton(Long id, UrediKartonRequest urediKartonRequest) {
        String errorNepostojeciKarton = String.format("Ne postoji pacijent sa id = '%d'", id);
        Optional<Pacijent> karton = pacijentRepository.findById(id);
        if(!karton.isPresent()) {
            throw new ResourceNotFoundException(errorNepostojeciKarton);
        }
        Pacijent trazeniKarton = karton.get();
        trazeniKarton.setIme(urediKartonRequest.getIme());
        trazeniKarton.setPrezime(urediKartonRequest.getPrezime());
        trazeniKarton.setDatumRodjenja(urediKartonRequest.getDatumRodjenja());
        trazeniKarton.setAdresa(urediKartonRequest.getAdresa());
        trazeniKarton.setBrojTelefona(urediKartonRequest.getBrojTelefona());
        trazeniKarton.setEmail(urediKartonRequest.getEmail());
        trazeniKarton.setSpol(urediKartonRequest.getSpol());
        trazeniKarton.setVisina(urediKartonRequest.getVisina());
        trazeniKarton.setTezina(urediKartonRequest.getTezina());
        trazeniKarton.setKrvnaGrupa(urediKartonRequest.getKrvnaGrupa());
        trazeniKarton.setHronicneBolesti(urediKartonRequest.getHronicneBolesti());
        trazeniKarton.setHronicnaTerapija(urediKartonRequest.getHronicnaTerapija());
        pacijentRepository.save(trazeniKarton);
        return new Response("Uspješno ste uredili karton!",200);

    }




}
