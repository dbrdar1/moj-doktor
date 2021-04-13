package ba.unsa.etf.doktordetalji;

import ba.unsa.etf.doktordetalji.models.Certifikat;
import ba.unsa.etf.doktordetalji.models.Doktor;
import ba.unsa.etf.doktordetalji.models.Edukacija;
import ba.unsa.etf.doktordetalji.repositories.CertifikatRepository;
import ba.unsa.etf.doktordetalji.repositories.DoktorRepository;
import ba.unsa.etf.doktordetalji.repositories.EdukacijaRepository;
import ba.unsa.etf.doktordetalji.requests.*;
import ba.unsa.etf.doktordetalji.services.KorisnikService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ServisiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    DoktorRepository doktorRepository;

    @Autowired
    CertifikatRepository certifikatRepository;

    @Autowired
    EdukacijaRepository edukacijaRepository;

    @Autowired
    KorisnikService korisnikService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getDoktorByIdTest() throws Exception {
        Doktor d = new Doktor(
                1L,
                "Test",
                "Test",
                new Date(1998, 5, 21),
                "Test",
                "0000000000",
                "test@mail",
                "Test",
                "Test");
        doktorRepository.save(d);
        Doktor d1 = doktorRepository.findByIme("Test");
        this.mockMvc.perform(get("/doktori/{id}", d1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ime", is(d1.getIme())));
        doktorRepository.delete(d1);
    }

    @Test
    public void ocijeniDoktoraTest() throws Exception {
        Doktor d = new Doktor(
                1L,
                "Test",
                "Test",
                new Date(1998, 5, 21),
                "Test",
                "0000000000",
                "test@mail",
                "Test",
                "Test");
        doktorRepository.save(d);
        Doktor d1 = doktorRepository.findByIme("Test");
        this.mockMvc.perform(put("/ocijeni-doktora")
                .content(asJsonString(new OcjenaRequest(d1.getId(), 5)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Uspješno ste ocijenili doktora!")));
        doktorRepository.delete(d1);
    }

    @Test
    public void ocijeniDoktoraPogresniPodaciTest() throws Exception {
        this.mockMvc.perform(put("/ocijeni-doktora")
                .content(asJsonString(new OcjenaRequest(-1L, 5)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Id doktora nije postojeći!")));
    }

    @Test
    public void dodajCertifikatTest() throws Exception {
        Doktor d = new Doktor(
                1L,
                "Test",
                "Test",
                new Date(1998, 5, 21),
                "Test",
                "0000000000",
                "test@mail",
                "Test",
                "Test");
        doktorRepository.save(d);
        Doktor d1 = doktorRepository.findByIme("Test");
        DodajCertifikatRequest dodajCertifikatRequest = new DodajCertifikatRequest(d1.getId(), "Test", "Test", 2020);
        this.mockMvc.perform(post("/dodaj-certifikat")
                .content(asJsonString(dodajCertifikatRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Uspješno ste dodali certifikat!")));
        doktorRepository.delete(d1);
    }

    @Test
    public void dodajCertifikatPogresniPodaciTest() throws Exception {
        DodajCertifikatRequest dodajCertifikatRequest = new DodajCertifikatRequest(-1L, "Test", "Test", 2020);
        this.mockMvc.perform(post("/dodaj-certifikat")
                .content(asJsonString(dodajCertifikatRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Id doktora nije postojeći!")));
    }

    @Test
    public void dodajEdukacijuTest() throws Exception {
        Doktor d = new Doktor(
                1L,
                "Test",
                "Test",
                new Date(1998, 5, 21),
                "Test",
                "0000000000",
                "test@mail",
                "Test",
                "Test");
        doktorRepository.save(d);
        Doktor d1 = doktorRepository.findByIme("Test");
        DodajEdukacijuRequest dodajEdukacijuRequest = new DodajEdukacijuRequest(d1.getId(), "Test", "Test", "Test", 2020, 2021, "Test", "Test");
        this.mockMvc.perform(post("/dodaj-edukaciju")
                .content(asJsonString(dodajEdukacijuRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Uspješno ste dodali edukaciju!")));
        doktorRepository.delete(d1);
    }

    @Test
    public void dodajEdukacijuPogresniPodaciTest() throws Exception {
        DodajEdukacijuRequest dodajEdukacijuRequest = new DodajEdukacijuRequest(-1L, "Test", "Test", "Test", 2020, 2021, "Test", "Test");
        this.mockMvc.perform(post("/dodaj-edukaciju")
                .content(asJsonString(dodajEdukacijuRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Id doktora nije postojeći!")));
    }

    @Test
    public void urediCertifikatTest() throws Exception {
        Certifikat test = new Certifikat("Test", "Test", 2020);
        certifikatRepository.save(test);
        Certifikat certifikat = certifikatRepository.findByNaziv("Test");
        UrediCertifikatRequest urediCertifikatRequest = new UrediCertifikatRequest(certifikat.getId(),"Test", "Test", 2000);
        this.mockMvc.perform(put("/uredi-certifikat")
                .content(asJsonString(urediCertifikatRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Uspješno ste uredili certifikat!")));
        certifikatRepository.delete(certifikat);
    }

    @Test
    public void urediCertifikatPogresniPodaciTest() throws Exception {
        UrediCertifikatRequest urediCertifikatRequest = new UrediCertifikatRequest(-1L,"Test", "Test", 2000);
        this.mockMvc.perform(put("/uredi-certifikat")
                .content(asJsonString(urediCertifikatRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Nepostojeći Id certifikata!")));
    }

    @Test
    public void urediEdukacijuTest() throws Exception {
        Edukacija test = new Edukacija("Test", "Test","Test", 2020, 2020, "Test", "Test");
        edukacijaRepository.save(test);
        Edukacija edukacija = edukacijaRepository.findByInstitucija("Test");
        UrediEdukacijuRequest urediEdukacijuRequest = new UrediEdukacijuRequest(edukacija.getId(),"Test", "Test","Test", 2020, 2020, "Test", "Test");
        this.mockMvc.perform(put("/uredi-edukaciju")
                .content(asJsonString(urediEdukacijuRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Uspješno ste uredili edukaciju!")));
        edukacijaRepository.delete(edukacija);
    }

    @Test
    public void urediEdukacijuPogresniPodaciTest() throws Exception {
        UrediEdukacijuRequest urediEdukacijuRequest = new UrediEdukacijuRequest(-1L,"Test", "Test","Test", 2020, 2020, "Test", "Test");
        this.mockMvc.perform(put("/uredi-edukaciju")
                .content(asJsonString(urediEdukacijuRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Nepostojeći Id edukacije!")));
    }

    @Test
    public void obrisiCertifikatTest() throws Exception {
        Certifikat test = new Certifikat("Test", "Test", 2020);
        certifikatRepository.save(test);
        Certifikat certifikat = certifikatRepository.findByNaziv("Test");
        this.mockMvc.perform(delete("/obrisi-certifikat/{id}", certifikat.getId()))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Uspješno ste obrisali certifikat!")));
    }

    @Test
    public void obrisiEdukacijuTest() throws Exception {
        Edukacija test = new Edukacija("Test", "Test","Test", 2020, 2020, "Test", "Test");
        edukacijaRepository.save(test);
        Edukacija edukacija = edukacijaRepository.findByInstitucija("Test");
        this.mockMvc.perform(delete("/obrisi-edukaciju/{id}", edukacija.getId()))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Uspješno ste obrisali edukaciju!")));
    }

    @Test
    public void obrisiCertifikatPogresniPodaciTest() throws Exception {
        this.mockMvc.perform(delete("/obrisi-certifikat/{id}", -1L))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Nepostojeći Id certifikata!")));
    }

    @Test
    public void obrisiEdukacijuPogresniPodaciTest() throws Exception {
        this.mockMvc.perform(delete("/obrisi-edukaciju/{id}", -1L))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Nepostojeći Id edukacije!")));
    }

    @Test
    public void urediPodatkeDoktoraTest() throws Exception {
        Doktor d = new Doktor(
                1L,
                "Test",
                "Test",
                new Date(1998, 5, 21),
                "Test",
                "0000000000",
                "test@mail",
                "Test",
                "Test");
        doktorRepository.save(d);
        Doktor d1 = doktorRepository.findByIme("Test");
        UrediPodatkeDoktoraRequest urediPodatkeDoktoraRequest = new UrediPodatkeDoktoraRequest(d1.getId(), "Test", "Test");
        this.mockMvc.perform(put("/uredi-osnovne-podatke-doktora")
                .content(asJsonString(urediPodatkeDoktoraRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Uspješno ste uredili osnovne podatke doktora!")));
        doktorRepository.delete(d1);
    }

    @Test
    public void urediPodatkeDoktoraPogresniPodaciTest() throws Exception {
        UrediPodatkeDoktoraRequest urediPodatkeDoktoraRequest = new UrediPodatkeDoktoraRequest(-1L, "Test", "Test");
        this.mockMvc.perform(put("/uredi-osnovne-podatke-doktora")
                .content(asJsonString(urediPodatkeDoktoraRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.poruka", is("Id doktora nije postojeći!")));
    }
}
