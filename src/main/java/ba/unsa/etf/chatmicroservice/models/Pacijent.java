package ba.unsa.etf.chatmicroservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@Table(name = "pacijent")
@JsonIgnoreProperties({"notifikacijas", "razgovors1", "razgovors2"})
public class Pacijent extends Korisnik {

    public Pacijent(String ime, String prezime, Date datumRodjenja, String adresa, String email, String brojTelefona) {
        super(ime, prezime, datumRodjenja, adresa, email, brojTelefona);
    }
}
