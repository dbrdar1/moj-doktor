package ba.unsa.etf.doktordetalji;


import ba.unsa.etf.doktordetalji.models.Doktor;
import ba.unsa.etf.doktordetalji.requests.FilterRequest;
import ba.unsa.etf.doktordetalji.responses.KorisnikResponse;
import ba.unsa.etf.doktordetalji.services.DoktorService;
import ba.unsa.etf.doktordetalji.services.KorisnikService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class KomunikacijaTest {

    @Mock
    private RestTemplate restTemplate;

    @Autowired
    private KorisnikService korisnikService;

    public static KorisnikResponse[] addX(int n, KorisnikResponse arr[], KorisnikResponse x)
    {
        int i;

        // create a new ArrayList
        List<KorisnikResponse> arrlist
                = new ArrayList<KorisnikResponse>(
                Arrays.asList(arr));

        // Add the new element
        arrlist.add(x);

        // Convert the Arraylist to array
        arr = arrlist.toArray(arr);

        // return the array
        return arr;
    }

    @Test
    public void komunikacijaTest() throws Exception {

        KorisnikResponse[] korisnikResponseList = {};
        korisnikResponseList = addX(0, korisnikResponseList, new KorisnikResponse(12L,
                "Samra",
                "Pusina",
                new Date(1998, 5, 21),
                "NekaAdresa",
                "061456321",
                "spusina1@etf.unsa.ba"));
        korisnikResponseList = addX(0, korisnikResponseList, new KorisnikResponse(13L,
                "Jusuf",
                "Delalic",
                new Date(1998, 7, 7),
                "NekaAdresa",
                "061456323",
                "jdelalic1@etf.unsa.ba"));

        Mockito
                .when(restTemplate.getForEntity(
                        "http://user-management/getDoktori", KorisnikResponse[].class))
                .thenReturn(new  ResponseEntity<KorisnikResponse[]>(korisnikResponseList, HttpStatus.OK));
        List<KorisnikResponse> doktoriIzBaze = korisnikService.getKorisnici();
        Assert.assertEquals(doktoriIzBaze, Arrays.asList(korisnikResponseList));
    }

}
