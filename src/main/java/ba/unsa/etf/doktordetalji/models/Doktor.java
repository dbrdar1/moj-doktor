package ba.unsa.etf.doktordetalji.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

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
}
