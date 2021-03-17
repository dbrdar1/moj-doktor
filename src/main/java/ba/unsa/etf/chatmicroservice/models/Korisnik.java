package ba.unsa.etf.chatmicroservice.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String ime;

    private String prezime;

    private Date datumRodjenja;

    private String adresa;

    private String brojTelefona;

    private String email;

    /*@OneToMany(cascade = CascadeType.ALL)
    private List<Notifikacija> notifikacijas;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Razgovor> razgovori1;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Razgovor> razgovori2;*/

    public Korisnik(String ime, String prezime, Date datumRodjenja, String adresa, String email, String brojTelefona) {
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.adresa = adresa;
        this.brojTelefona = brojTelefona;
        this.email = email;
    }

    public Korisnik() { }
}
