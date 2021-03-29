package ba.unsa.etf.chatmicroservice.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DodajRazgovorRequest {

    private Long idPrvogKorisnika;
    private Long idDrugogKorisnika;
}
