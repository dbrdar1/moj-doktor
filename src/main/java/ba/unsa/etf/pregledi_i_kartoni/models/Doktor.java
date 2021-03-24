package ba.unsa.etf.pregledi_i_kartoni.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "doktor")
public class Doktor extends Korisnik {
    public Doktor(String ime, String prezime, Date datumRodjenja, String adresa,
                  String brojTelefona, String mail) {
        super(ime, prezime, datumRodjenja, adresa, brojTelefona, mail);
    }
}
