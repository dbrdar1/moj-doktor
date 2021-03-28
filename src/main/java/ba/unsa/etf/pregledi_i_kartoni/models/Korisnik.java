package ba.unsa.etf.pregledi_i_kartoni.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "korisnik", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "email"
        })})


@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String ime;

    @NotBlank
    private String prezime;

    private Date datumRodjenja;

    @NotBlank
    private String adresa;

    @NotBlank
    private String brojTelefona;

    @NotBlank
    private String email;

    public Korisnik(String ime, String prezime, Date datumRodjenja, String adresa, String brojTelefona, String email) {
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.adresa = adresa;
        this.brojTelefona = brojTelefona;
        this.email = email;
    }
}

