package ba.unsa.etf.doktordetalji.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor

public class ListaKorisnikResponse {
    private List<KorisnikResponse> korisnikResponses;

    public ListaKorisnikResponse() {
        korisnikResponses = new ArrayList<>();
    }
}
