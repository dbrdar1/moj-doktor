package ba.unsa.etf.termini.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
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

    //  @Column(nullable = false)
    private String naslov;

    // @Column(nullable = false)
    private String tekst;

    //   @Column(nullable = false)
    private Date datum;

    //@Column(nullable = false)
    private String vrijeme;

    @ManyToOne(cascade = {CascadeType.ALL},fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "korisnik_id", nullable = false)
    private Korisnik korisnik;

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
