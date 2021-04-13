package ba.unsa.etf.doktordetalji.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CertifikatiResponse {
    private String institucija;
    private String naziv;
    private Integer godinaIzdavanja;
}
