package ba.unsa.etf.chatmicroservice.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DodajPorukuRequest {

    private String sadrzaj;
    private Date datum;
    private String vrijeme;
    private Long idPosiljaoca;
    private Long idPrimaoca;
}
