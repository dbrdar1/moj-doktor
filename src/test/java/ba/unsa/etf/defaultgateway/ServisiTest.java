package ba.unsa.etf.defaultgateway;

import ba.unsa.etf.defaultgateway.models.Korisnik;
import ba.unsa.etf.defaultgateway.repositories.DoktorRepository;
import ba.unsa.etf.defaultgateway.requests.GetResetTokenRequest;
import ba.unsa.etf.defaultgateway.requests.LoginRequest;
import ba.unsa.etf.defaultgateway.requests.SpasiLozinkuRequest;
import ba.unsa.etf.defaultgateway.requests.VerificirajPodatkeRequest;
import ba.unsa.etf.defaultgateway.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ServisiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private DoktorRepository doktorRepository;

    ServisiTest() {
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void loginTest() throws Exception {
        this.mockMvc.perform(post("/login")
                .content(asJsonString(new LoginRequest("spusina1", "password")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vrstaTokena", is("Bearer")));
    }

    @Test
    public void unauthorizedLoginTest() throws Exception {
        this.mockMvc.perform(post("/login")
                .content(asJsonString(new LoginRequest("spusina", "password")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void resetTokenTest() throws Exception {
        Korisnik korisnik = new Korisnik();
        korisnik.setKorisnickoIme("spusina1");
        korisnik.setLozinka("password");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        korisnik.getKorisnickoIme(),
                        korisnik.getLozinka()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        this.mockMvc.perform(post("/reset-token")
                .header("Authorization", "Bearer " + token)
                .content(asJsonString(new GetResetTokenRequest("spusina1")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.poruka", is("Token je poslan!")));
    }

    @Test
    public void resetTokenPogresniPodaciTest() throws Exception {
        Korisnik korisnik = new Korisnik();
        korisnik.setKorisnickoIme("spusina1");
        korisnik.setLozinka("password");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        korisnik.getKorisnickoIme(),
                        korisnik.getLozinka()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        this.mockMvc.perform(post("/reset-token")
                .header("Authorization", "Bearer " + token)
                .content(asJsonString(new GetResetTokenRequest("test")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.poruka", is("Korisničko ime ili email koji ste unijeli nije validan. Provjerite i pokušajte ponovo!")));
    }

    @Test
    public void verifikacijskiPodaciTest() throws Exception {
        Korisnik korisnik = new Korisnik();
        korisnik.setKorisnickoIme("spusina1");
        korisnik.setLozinka("password");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        korisnik.getKorisnickoIme(),
                        korisnik.getLozinka()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        this.mockMvc.perform(post("/verifikacijski-podaci")
                .header("Authorization", "Bearer " + token)
                .content(asJsonString(new VerificirajPodatkeRequest("spusina1", "123")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Neispravni verifikacijski podaci!")));
    }

    @Test
    public void lozinkaTest() throws Exception {
        Korisnik korisnik = new Korisnik();
        korisnik.setKorisnickoIme("spusina1");
        korisnik.setLozinka("password");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        korisnik.getKorisnickoIme(),
                        korisnik.getLozinka()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        this.mockMvc.perform(put("/lozinka")
                .header("Authorization", "Bearer " + token)
                .content(asJsonString(new SpasiLozinkuRequest("spusina1", "password")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Lozinka uspješno promijenjena!")));
    }

    @Test
    public void lozinkaPogresniPodaciTest() throws Exception {
        Korisnik korisnik = new Korisnik();
        korisnik.setKorisnickoIme("spusina1");
        korisnik.setLozinka("password");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        korisnik.getKorisnickoIme(),
                        korisnik.getLozinka()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        this.mockMvc.perform(put("/lozinka")
                .header("Authorization", "Bearer " + token)
                .content(asJsonString(new SpasiLozinkuRequest("test", "password")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Korisničko ime ili email koji ste unijeli nije validan. Provjerite i pokušajte ponovo!")));
    }

}


