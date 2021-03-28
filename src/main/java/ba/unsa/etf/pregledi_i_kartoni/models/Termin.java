package ba.unsa.etf.pregledi_i_kartoni.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "termin")
public class Termin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date datumPregleda;

    private String vrijemePregleda;

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PacijentDoktor pacijentDoktor;

    public Termin(Date datumPregleda, String vrijemePregleda, PacijentDoktor pacijentDoktor) {
        this.datumPregleda = datumPregleda;
        this.vrijemePregleda = vrijemePregleda;
        this.pacijentDoktor = pacijentDoktor;
    }

}
