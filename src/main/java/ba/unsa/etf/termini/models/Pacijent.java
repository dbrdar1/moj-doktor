package ba.unsa.etf.termini.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pacijent")
@JsonIgnoreProperties({"vezeSaDoktorima"})
public class Pacijent extends Korisnik {

    public Pacijent(String ime, String prezime, Date datumRodjenja, String adresa, String brojTelefona, String mail){
        super(ime, prezime, datumRodjenja, adresa, brojTelefona, mail);
    }

    @OneToMany(mappedBy = "pacijent", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PacijentKartonDoktor> vezeSaDoktorima = new ArrayList<>();
}