package ba.unsa.etf.pregledi_i_kartoni.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Termin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date datumPregleda;

    private String vrijemePregleda;

    @ManyToOne(cascade = CascadeType.ALL)
    private PacijentKartonDoktor pacijentDoktor;

    public Termin(Date datumPregleda, String vrijemePregleda, PacijentKartonDoktor pacijentDoktor) {
        this.datumPregleda = datumPregleda;
        this.vrijemePregleda = vrijemePregleda;
        this.pacijentDoktor = pacijentDoktor;
    }

}
