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
@Table(name = "pacijent")
public class Pacijent extends Korisnik {

    public Pacijent(String ime, String prezime, Date datumRodjenja, String adresa, String brojTelefona, String mail){
        super(ime, prezime, datumRodjenja, adresa, brojTelefona, mail);
    }

    @OneToMany(targetEntity = PacijentKartonDoktor.class,
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER,
            mappedBy="pacijent")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<PacijentKartonDoktor> vezeSaDoktorima;
}