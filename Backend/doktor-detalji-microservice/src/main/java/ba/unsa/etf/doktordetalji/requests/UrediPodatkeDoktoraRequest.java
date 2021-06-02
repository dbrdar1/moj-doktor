package ba.unsa.etf.doktordetalji.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrediPodatkeDoktoraRequest {
    private Long idDoktora;
    private String titula;
    private String biografija;
}
