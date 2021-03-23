package ba.unsa.etf.doktordetalji.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doktor")
public class Doktor extends Korisnik{

    @NotBlank
    private String titula;

    private String biografija;

    public Doktor(String ime, String prezime, Date datumRodjenja, String adresa, String brojTelefona, String mail, String titula, String biografija){
        super(ime, prezime, datumRodjenja, adresa, brojTelefona, mail);
        this.titula = titula;
        this.biografija = biografija;
    }

    @OneToMany(mappedBy = "doktor", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Certifikat> certifikati = new ArrayList<>();

    @OneToMany(mappedBy = "doktor", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Edukacija> edukacije = new ArrayList<>();

    @OneToMany(mappedBy = "doktor", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Ocjena> ocjene = new ArrayList<>();
}
