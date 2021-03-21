package ba.unsa.etf.chatmicroservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifikacija")
public class Notifikacija {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String naziv;

    @NotBlank
    private String tekst;

    private Date datum;

    @NotBlank
    private String vrijeme;

    @ManyToOne
    @JoinColumn(name = "korisnikId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Korisnik korisnik;

    public Notifikacija(String naziv, String tekst, Date datum, String vrijeme, Korisnik korisnik) {
        this.naziv = naziv;
        this.tekst = tekst;
        this.datum = datum;
        this.vrijeme = vrijeme;
        this.korisnik = korisnik;
    }
}
