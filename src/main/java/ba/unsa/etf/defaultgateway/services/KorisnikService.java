package ba.unsa.etf.defaultgateway.services;

import ba.unsa.etf.defaultgateway.exceptions.ResourceNotFoundException;
import ba.unsa.etf.defaultgateway.models.*;
import ba.unsa.etf.defaultgateway.repositories.*;
import ba.unsa.etf.defaultgateway.requests.*;
import ba.unsa.etf.defaultgateway.responses.KorisnikResponse;
import ba.unsa.etf.defaultgateway.responses.Response;
import ba.unsa.etf.defaultgateway.security.JwtTokenProvider;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;
    private final KorisnickaUlogaRepository korisnickaUlogaRepository;
    private final DoktorRepository doktorRepository;
    private final PacijentRepository pacijentRepository;


    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public String pripremiUloge() {
        KorisnickaUloga doktor = korisnickaUlogaRepository.findByNazivKorisnickeUloge(NazivKorisnickeUloge.ROLE_DOKTOR);
        KorisnickaUloga pacijent = korisnickaUlogaRepository.findByNazivKorisnickeUloge(NazivKorisnickeUloge.ROLE_PACIJENT);

        if(doktor == null){
            KorisnickaUloga u1 = new KorisnickaUloga(1L, NazivKorisnickeUloge.ROLE_DOKTOR);
            korisnickaUlogaRepository.save(u1);
        }
        if(pacijent == null){
            KorisnickaUloga u2 = new KorisnickaUloga(3L, NazivKorisnickeUloge.ROLE_PACIJENT);
            korisnickaUlogaRepository.save(u2);
        }
        return "Baza spremna!";
    }

    public String inicijalizirajBazu() {

        korisnikRepository.deleteAllInBatch();
        korisnikRepository.flush();

        doktorRepository.deleteAllInBatch();
        doktorRepository.flush();

        pacijentRepository.deleteAllInBatch();
        pacijentRepository.flush();

        korisnickaUlogaRepository.deleteAllInBatch();
        korisnickaUlogaRepository.flush();

        String lozinka = "password";

        Doktor d = new Doktor(
                "Samra",
                "Pusina",
                new Date(1998, 5, 21),
                "NekaAdresa",
                "061456321",
                "spusina1@etf.unsa.ba",
                "spusina1",
                "password");

        Doktor d2 = new Doktor(
                "Jusuf",
                "Delalic",
                new Date(1998, 7, 7),
                "NekaAdresa",
                "061456323",
                "jdelalic1@etf.unsa.ba",
                "jdelalic1",
                "password");

        Pacijent p = new Pacijent(
                "Esmina",
                "Radusic",
                new Date(1998, 5, 21),
                "NekaAdresa",
                "061456322",
                "eradusi1@etf.unsa.ba",
                "eradusic1",
                "password");

        Pacijent p2 = new Pacijent(
                "Dzavid",
                "Brdar",
                new Date(1998, 5, 2),
                "NekaAdresa",
                "061456324",
                "dbrdar1@etf.unsa.ba",
                "dbrdar1",
                "password");

        d.setLozinka(passwordEncoder.encode(d.getLozinka()));
        d2.setLozinka(passwordEncoder.encode(d2.getLozinka()));
        p.setLozinka(passwordEncoder.encode(p.getLozinka()));
        p2.setLozinka(passwordEncoder.encode(p2.getLozinka()));

        KorisnickaUloga u1 = new KorisnickaUloga(0L, NazivKorisnickeUloge.ROLE_DOKTOR);
        KorisnickaUloga u2 = new KorisnickaUloga(1L, NazivKorisnickeUloge.ROLE_PACIJENT);

        korisnickaUlogaRepository.save(u1);
        korisnickaUlogaRepository.save(u2);

        List<KorisnickaUloga> uloge = Collections.singletonList(korisnickaUlogaRepository.findByNazivKorisnickeUloge(NazivKorisnickeUloge.ROLE_DOKTOR));
        d.setUloge(new HashSet<>(uloge));
        d2.setUloge(new HashSet<>(uloge));

        List<KorisnickaUloga> uloge2 = Collections.singletonList(korisnickaUlogaRepository.findByNazivKorisnickeUloge(NazivKorisnickeUloge.ROLE_PACIJENT));
        p.setUloge(new HashSet<>(uloge2));
        p2.setUloge(new HashSet<>(uloge2));

        doktorRepository.save(d);
        doktorRepository.save(d2);
        pacijentRepository.save(p);
        pacijentRepository.save(p2);

        return "Inicijalizacija baze zavrsena!";
    }

    public String autentificirajKorisnika(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getKorisnickoIme(),
                        loginRequest.getLozinka()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    public Korisnik getKorisnikByKorisnickoIme(String username) {
        String errorMessage = String.format("Nepostojeće korisničko ime '%s'", username);
        return korisnikRepository
                .findByKorisnickoIme(username)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage));
    }

    public Response generirajResetToken(GetResetTokenRequest getResetTokenRequest) throws MessagingException, IOException, TemplateException, javax.mail.MessagingException {
        Optional<Korisnik> user = korisnikRepository.findByKorisnickoIme(getResetTokenRequest.getUserInfo());
        if (!user.isPresent()) {
            user = korisnikRepository.findByEmail(getResetTokenRequest.getUserInfo());
            if (!user.isPresent()) {
                return new Response("Korisničko ime ili email koji ste unijeli nije validan. Provjerite i pokušajte ponovo!", 400);
            }
        }
        String resetToken = UUID.randomUUID().toString();
        user.get().setResetToken(resetToken);
        korisnikRepository.save(user.get());
        System.out.println(user.get().getEmail());
        mailService.sendmail(user.get().getEmail(), user.get().getIme(), resetToken);
        return new Response("Token je poslan!", 200);

    }

    public Response verificirajPodatke(VerificirajPodatkeRequest verificirajPodatkeRequest) {

        Optional<Korisnik> user = korisnikRepository.findByKorisnickoIme(verificirajPodatkeRequest.getUserInfo());
        if (!user.isPresent()) {
            user = korisnikRepository.findByEmail(verificirajPodatkeRequest.getUserInfo());
            if (!user.isPresent()) {
                return new Response("Neispravni verifikacijski podaci!", 400);
            }
        }
        if (user.get().getResetToken().equals(verificirajPodatkeRequest.getResetToken())) {
            return new Response("OK", 200);
        }
        return new Response("Neispravni verifikacijski podaci!", 400);
    }

    public Response promijeniLozinku(SpasiLozinkuRequest spasiLozinkuRequest) {

        Optional<Korisnik> user = korisnikRepository.findByKorisnickoIme(spasiLozinkuRequest.getUserInfo());
        if (!user.isPresent()) {
            user = korisnikRepository.findByEmail(spasiLozinkuRequest.getUserInfo());
            if (!user.isPresent()) {
                return new Response("Korisničko ime ili email koji ste unijeli nije validan. Provjerite i pokušajte ponovo!", 400);
            }
        }
        user.get().setLozinka(passwordEncoder.encode(spasiLozinkuRequest.getNovaLozinka()));
        user.get().setResetToken("");
        korisnikRepository.save(user.get());
        return new Response("Lozinka uspješno promijenjena!", 200);
    }

    public List<KorisnikResponse> getKorisnici(String uloga) {
        if (uloga.equals("")) {
            List<Korisnik> korisnikList = korisnikRepository.findAll();
            return korisnikList
                    .stream()
                    .map(korisnik -> new KorisnikResponse(korisnik.getId(), korisnik.getIme(), korisnik.getPrezime(), korisnik.getDatumRodjenja(), korisnik.getAdresa(), korisnik.getBrojTelefona(), korisnik.getEmail()))
                    .collect(Collectors.toList());
        } else if (uloga.equalsIgnoreCase("DOKTOR")) {
            List<Doktor> korisnikList = doktorRepository.findAll();
            return korisnikList
                    .stream()
                    .map(korisnik -> new KorisnikResponse(korisnik.getId(), korisnik.getIme(), korisnik.getPrezime(), korisnik.getDatumRodjenja(), korisnik.getAdresa(), korisnik.getBrojTelefona(), korisnik.getEmail()))
                    .collect(Collectors.toList());
        } else if (uloga.equalsIgnoreCase("PACIJENT")) {
            List<Pacijent> korisnikList = pacijentRepository.findAll();
            return korisnikList
                    .stream()
                    .map(korisnik -> new KorisnikResponse(korisnik.getId(), korisnik.getIme(), korisnik.getPrezime(), korisnik.getDatumRodjenja(), korisnik.getAdresa(), korisnik.getBrojTelefona(), korisnik.getEmail()))
                    .collect(Collectors.toList());
        } else return new ArrayList<>();
    }


    public String registrujKorisnika(RegistracijaRequest registracijaRequest) {
        Optional<Korisnik> korisnik = korisnikRepository.findByKorisnickoIme(registracijaRequest.getKorisnickoIme());
        if(korisnik.isPresent()) return "Već postoji korisnik s ovim korisničkim imenom.";

        Optional<Korisnik> korisnik1 = korisnikRepository.findByEmail(registracijaRequest.getEmail());
        if(korisnik1.isPresent()) return "Već postoji korisnik s ovim email-om.";

        if(registracijaRequest.getUloga().equals("DOKTOR")){
            Doktor d = new Doktor(
                    registracijaRequest.getIme(),
                    registracijaRequest.getPrezime(),
                    registracijaRequest.getDatumRodjenja(),
                    registracijaRequest.getAdresa(),
                    registracijaRequest.getBrojTelefona(),
                    registracijaRequest.getEmail(),
                    registracijaRequest.getKorisnickoIme(),
                    registracijaRequest.getLozinka());
            d.setLozinka(passwordEncoder.encode(d.getLozinka()));
            List<KorisnickaUloga> uloge = Collections.singletonList(korisnickaUlogaRepository.findByNazivKorisnickeUloge(NazivKorisnickeUloge.ROLE_DOKTOR));
            d.setUloge(new HashSet<>(uloge));
            doktorRepository.save(d);
        }
        else{
            Pacijent p = new Pacijent(
                    registracijaRequest.getIme(),
                    registracijaRequest.getPrezime(),
                    registracijaRequest.getDatumRodjenja(),
                    registracijaRequest.getAdresa(),
                    registracijaRequest.getBrojTelefona(),
                    registracijaRequest.getEmail(),
                    registracijaRequest.getKorisnickoIme(),
                    registracijaRequest.getLozinka());
            p.setLozinka(passwordEncoder.encode(p.getLozinka()));
            List<KorisnickaUloga> uloge = Collections.singletonList(korisnickaUlogaRepository.findByNazivKorisnickeUloge(NazivKorisnickeUloge.ROLE_PACIJENT));
            p.setUloge(new HashSet<>(uloge));
            pacijentRepository.save(p);
        }
      return "Uspješna registracija!";
    }

}
