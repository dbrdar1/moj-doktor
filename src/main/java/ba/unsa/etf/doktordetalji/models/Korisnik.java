package ba.unsa.etf.doktordetalji.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "korisnik")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="uloga",
        discriminatorType = DiscriminatorType.STRING)
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

    public Korisnik(String ime, String prezime, Date datumRodjenja, String adresa, String brojTelefona,  String email) {
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.adresa = adresa;
        this.brojTelefona = brojTelefona;
        this.email = email;

    }
}
