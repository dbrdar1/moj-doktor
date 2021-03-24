package ba.unsa.etf.chatmicroservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "razgovor")
@JsonIgnoreProperties({"porukas"})
public class Razgovor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prviKorisnikId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Korisnik prviKorisnik;

    @ManyToOne
    @JoinColumn(name = "drugiKorisnikId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Korisnik drugiKorisnik;

    @OneToMany(targetEntity = Poruka.class,
            cascade = CascadeType.ALL,
            mappedBy = "razgovor")
    private List<Poruka> porukas = new ArrayList<>();

    public Razgovor(Korisnik prviKorisnik, Korisnik drugiKorisnik) {
        this.prviKorisnik = prviKorisnik;
        this.drugiKorisnik = drugiKorisnik;
    }
}
