package ba.unsa.etf.termini.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Data
@Table(name = "notifikacija")
public class Notifikacija {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Korisnik dajKorisnika() {
        return korisnik;
    }

    public void postaviKorisnika(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    @NotBlank(message = "Notifikacija mora imati naslov")
    private String naslov;

    @Size(min = 10, max = 500, message = "Tekst notifikacije mora biti izmedju 10 i 500 karaktera")
    private String tekst;

    @NotNull(message = "Notifikacija mora imati datum")
    private Date datum;

    @NotBlank(message = "Notifikacija mora imati vrijeme")
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$",
            message = "Vrijeme mora biti zadano u ispravnom formatu (HH:MM)")
    private String vrijeme;

    @ManyToOne
    @JoinColumn(name = "korisnik_id", nullable = false)
    @JsonIgnore
    private Korisnik korisnik;

    public Notifikacija(String naslov, String tekst, Date datum, String vrijeme) {
        this.naslov = naslov;
        this.tekst = tekst;
        this.datum = datum;
        this.vrijeme = vrijeme;
    }

    public Notifikacija(String naslov, String tekst, Date d, String t, Korisnik korisnik) {
        this.naslov = naslov;
        this.tekst = tekst;
        this.korisnik = korisnik;
        this.datum=d;
        this.vrijeme=t;
    }

    public Notifikacija() {

    }
}
