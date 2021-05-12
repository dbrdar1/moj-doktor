package ba.unsa.etf.termini.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name = "doktor")
public class Doktor extends Korisnik{
    private String titula;

    public Doktor(String ime, String prezime, Date datumRodjenja, String adresa, String brojTelefona, String mail, String titula){
        super(ime, prezime, datumRodjenja, adresa, brojTelefona, mail);
        this.titula = titula;
    }

    @OneToMany(mappedBy = "doktor", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PacijentKartonDoktor> vezeSaPacijentima = new ArrayList<>();
}
