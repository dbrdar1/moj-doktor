package ba.unsa.etf.termini.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "korisnik" ,uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "email"
        })})
@Inheritance(strategy = InheritanceType.JOINED)
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

    @OneToMany(targetEntity = Notifikacija.class,
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER,
            mappedBy="korisnik")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Notifikacija> notifikacije;
}

