package ba.unsa.etf.doktordetalji.models;

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
@DiscriminatorValue("doktor")
public class Doktor extends Korisnik{

    private String titula;

    private String biografija;

    private Integer ocjena;

    public Doktor(String ime, String prezime, Date datumRodjenja, String adresa, String brojTelefona, String mail, String titula, String biografija, Integer ocjena){
        super(ime, prezime, datumRodjenja, adresa, brojTelefona, mail);
        this.titula = titula;
        this.biografija = biografija;
        this.ocjena = ocjena;
    }

    @OneToMany(mappedBy = "doktor", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Certifikat> certifikati = new ArrayList<>();

    @OneToMany(mappedBy = "doktor", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Edukacija> edukacije = new ArrayList<>();
}
