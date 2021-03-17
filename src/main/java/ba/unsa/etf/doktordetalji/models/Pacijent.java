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
@Table(name = "pacijent")
@DiscriminatorValue("pacijent")
public class Pacijent extends Korisnik{

    public Pacijent(String ime, String prezime, Date datumRodjenja, String adresa, String brojTelefona, String mail){
        super(ime, prezime, datumRodjenja, adresa, brojTelefona, mail);
    }
}
