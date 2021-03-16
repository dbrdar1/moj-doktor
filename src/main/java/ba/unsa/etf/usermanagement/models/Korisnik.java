package ba.unsa.etf.usermanagement.models;

import lombok.Data;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

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

    private String korisnickoIme;

    private String lozinka;

    private  String resetToken = "";

    public Korisnik(String ime, String prezime, Date datumRodjenja, String adresa, String email, String brojTelefona, String korisnickoIme, String lozinka) {
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.adresa = adresa;
        this.brojTelefona = brojTelefona;
        this.email = email;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public Korisnik() {

    }
}
