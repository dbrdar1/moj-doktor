package test.ba.unsa.etf.pregledi_i_kartoni;

import ba.unsa.etf.pregledi_i_kartoni.models.Doktor;
import ba.unsa.etf.pregledi_i_kartoni.models.Pacijent;
import ba.unsa.etf.pregledi_i_kartoni.models.PacijentDoktor;
import ba.unsa.etf.pregledi_i_kartoni.models.Termin;
import ba.unsa.etf.pregledi_i_kartoni.repositories.*;
import ba.unsa.etf.pregledi_i_kartoni.requests.DodajPregledRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


@SpringBootTest
@AutoConfigureMockMvc
public class PregledTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    TerminRepository terminRepository;
    @Autowired
    DoktorRepository doktorRepository;
    @Autowired
    PacijentRepository pacijentRepository;
    @Autowired
    PacijentDoktorRepository pacijentDoktorRepository;
    @Autowired
    PregledRepository pregledRepository;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void postIspravnogPregleda() throws Exception {
        Pacijent p = new Pacijent("test1", "test", new Date(), "adresa", "033211211", "neko1@gmail.com");
        Doktor d = new Doktor("test2", "test", new Date(), "adresa", "033211211", "neko2@gmail.com");
        pacijentRepository.save(p);
        doktorRepository.save(d);
        p = pacijentRepository.findByIme("test1").get();
        d = doktorRepository.findByIme("test2").get();
        PacijentDoktor pd = new PacijentDoktor();
        pd.setDoktor(d);
        pd.setPacijent(p);
        pacijentDoktorRepository.save(pd);
        pd = pacijentDoktorRepository.findByPacijent(p).get();
        Termin t = new Termin();
        t.setPacijentDoktor(pd);
        t.setDatumPregleda(new Date());
        t.setVrijemePregleda("13:00");

        this.mockMvc.perform(post("/dodaj-pregled")
                .content(asJsonString(new DodajPregledRequest(t.getId(),
                        "bol u leđima i očima",
                        "ne izgleda dobro ni sekunde",
                        "rana faza skolioze i negativna dioptrija",
                        "manje sjediti za računarom, više se kretati",
                        "no comment")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        pacijentRepository.delete(p);
        doktorRepository.delete(d);
        terminRepository.delete(t);
    }

    @Test
    public void postNeispravnihFormata() throws Exception {
        Pacijent p = new Pacijent("test1", "test", new Date(), "adresa", "033211211", "neko1@gmail.com");
        Doktor d = new Doktor("test2", "test", new Date(), "adresa", "033211211", "neko2@gmail.com");
        pacijentRepository.save(p);
        doktorRepository.save(d);
        p = pacijentRepository.findByIme("test1").get();
        d = doktorRepository.findByIme("test2").get();
        PacijentDoktor pd = new PacijentDoktor();
        pd.setDoktor(d);
        pd.setPacijent(p);
        pacijentDoktorRepository.save(pd);
        pd = pacijentDoktorRepository.findByPacijent(p).get();
        Termin t = new Termin();

        t.setPacijentDoktor(pd);
        t.setDatumPregleda(new Date());
        t.setVrijemePregleda("13:00");


        this.mockMvc.perform(post("/dodaj-termin")
                .content(asJsonString(new DodajPregledRequest(null,
                        "bol u leđima i očima",
                        "ne izgleda dobro ni sekunde",
                        "rana faza skolioze i negativna dioptrija",
                        "manje sjediti za računarom, više se kretati",
                        "no comment")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.statusCode", is(500)));
        this.mockMvc.perform(post("/dodaj-termin")
                .content(asJsonString(new DodajPregledRequest(t.getId(),
                        "bol u leđima i očima",
                        "",
                        "rana faza skolioze i negativna dioptrija",
                        "manje sjediti za računarom, više se kretati",
                        "no comment")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.statusCode", is(500)));
        this.mockMvc.perform(post("/dodaj-termin")
                .content(asJsonString(new DodajPregledRequest(t.getId(),
                        "bol u leđima i očima",
                        "ne izgleda dobro ni sekunde",
                        "rana faza skolioze i negativna dioptrija",
                        "",
                        "no comment")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.statusCode", is(500)));
        pacijentRepository.delete(p);
        doktorRepository.delete(d);
        terminRepository.delete(t);
    }

    @Test
    public void getPregleda() throws Exception {
        Pacijent p = new Pacijent("test1", "test", new Date(), "adresa", "033211211", "neko1@gmail.com");
        Doktor d = new Doktor("test2", "test", new Date(), "adresa", "033211211", "neko2@gmail.com");
        pacijentRepository.save(p);
        doktorRepository.save(d);
        p = pacijentRepository.findByIme("test1").get();
        d = doktorRepository.findByIme("test2").get();
        PacijentDoktor pd = new PacijentDoktor();
        pd.setDoktor(d);
        pd.setPacijent(p);
        pacijentDoktorRepository.save(pd);
        pd = pacijentDoktorRepository.findByPacijent(p).get();
        Termin t = new Termin();
        t.setPacijentDoktor(pd);
        t.setDatumPregleda(new Date());
        t.setVrijemePregleda("13:00");


        this.mockMvc.perform(post("/dodaj-termin")
                .content(asJsonString(new DodajPregledRequest(t.getId(),
                        "bol u leđima i očima",
                        "ne izgleda dobro ni sekunde",
                        "rana faza skolioze i negativna dioptrija",
                        "manje sjediti za računarom, više se kretati",
                        "no comment")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/pregled/" + p.getId()))
                .andDo(print())
                .andExpect(jsonPath("$.pregled", hasSize(1)));
        this.mockMvc.perform(get("/pregled?idDoktor=" + d.getId()))
                .andDo(print())
                .andExpect(jsonPath("$.pregled", hasSize(1)));
        pacijentRepository.delete(p);
        doktorRepository.delete(d);
        terminRepository.delete(t);
    }


}