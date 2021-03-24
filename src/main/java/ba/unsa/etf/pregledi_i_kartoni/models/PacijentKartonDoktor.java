package ba.unsa.etf.pregledi_i_kartoni.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class PacijentKartonDoktor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Doktor doktor;

    @ManyToOne(cascade = CascadeType.ALL)
    private KartonPacijent pacijent;

    public PacijentKartonDoktor(Doktor doktor, KartonPacijent pacijent) {

        this.doktor = doktor;
        this.pacijent = pacijent;

    }

    public PacijentKartonDoktor() {

    }
}
