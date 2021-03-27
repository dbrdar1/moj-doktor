package ba.unsa.etf.termini.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name = "termin")
public class Termin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Termin mora imati datum")
    private Date datum;

    @NotBlank(message = "Termin mora imati vrijeme")
    private String vrijeme;

    @ManyToOne
    @JsonIgnore
    private PacijentKartonDoktor pacijentKartonDoktor;

    public Termin(Date datumPregleda, String vrijemePregleda, PacijentKartonDoktor pacijentKartonDoktor) {
        this.datum = datumPregleda;
        this.vrijeme = vrijemePregleda;
        this.pacijentKartonDoktor = pacijentKartonDoktor;
    }

    public Termin() {

    }
}
