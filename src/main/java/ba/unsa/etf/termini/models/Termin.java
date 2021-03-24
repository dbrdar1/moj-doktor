package ba.unsa.etf.termini.models;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
public class Termin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Termin mora imati datum")
    private Date datum;

    @NotNull(message = "Termin mora imati vrijeme")
    @NotEmpty(message = "Termin mora imati vrijeme")
    private String vrijeme;

    @ManyToOne(cascade = {CascadeType.ALL},fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pacijent_karton_doktor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PacijentKartonDoktor pacijentKartonDoktor;

    public Termin(Date datumPregleda, String vrijemePregleda, PacijentKartonDoktor pacijentKartonDoktor) {
        this.datum = datumPregleda;
        this.vrijeme = vrijemePregleda;
        this.pacijentKartonDoktor = pacijentKartonDoktor;
    }

    public Termin() {

    }
}
