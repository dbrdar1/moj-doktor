package ba.unsa.etf.chatmicroservice.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Razgovor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Korisnik prviKorisnik;

    @ManyToOne(cascade = CascadeType.ALL)
    private Korisnik drugiKorisnik;

    public Razgovor(Korisnik prviKorisnik, Korisnik drugiKorisnik) {
        this.prviKorisnik = prviKorisnik;
        this.drugiKorisnik = drugiKorisnik;
    }

    public Razgovor() { }
}
