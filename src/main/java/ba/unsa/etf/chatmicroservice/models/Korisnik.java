package ba.unsa.etf.chatmicroservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "korisnik", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "email"
        })
})
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({"notifikacije", "poslanePoruke", "primljenePoruke"})
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String ime;

    @NotBlank
    private String prezime;

    @NotNull
    private Date datumRodjenja;

    @NotBlank
    private String adresa;

    @NotBlank
    private String brojTelefona;

    @NotBlank
    @Email(message = "Email mora biti validan")
    private String email;

    @OneToMany(targetEntity = Notifikacija.class, cascade = CascadeType.ALL, mappedBy = "korisnik")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Notifikacija> notifikacije = new ArrayList<>();

    @OneToMany(targetEntity = Poruka.class, cascade = CascadeType.ALL, mappedBy = "posiljalac")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Poruka> poslanePoruke = new ArrayList<>();

    @OneToMany(targetEntity = Poruka.class, cascade = CascadeType.ALL, mappedBy = "primalac")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Poruka> primljenePoruke = new ArrayList<>();

    public Korisnik(String ime, String prezime, Date datumRodjenja, String adresa, String email, String brojTelefona) {
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.adresa = adresa;
        this.brojTelefona = brojTelefona;
        this.email = email;
    }
}
