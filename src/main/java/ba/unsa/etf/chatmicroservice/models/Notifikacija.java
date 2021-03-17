package ba.unsa.etf.chatmicroservice.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Notifikacija {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String naziv;

    private String tekst;

    @ManyToOne(cascade = CascadeType.ALL)
    private Korisnik korisnik;

    public Notifikacija(String naziv, String tekst, Korisnik korisnik) {
        this.naziv = naziv;
        this.tekst = tekst;
        this.korisnik = korisnik;
    }

    public Notifikacija() { }
}
