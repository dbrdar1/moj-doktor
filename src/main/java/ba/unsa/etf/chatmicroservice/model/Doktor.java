package ba.unsa.etf.chatmicroservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@Table(name = "doktor")
@JsonIgnoreProperties({"notifikacijas", "razgovors1", "razgovors2"})
public class Doktor extends Korisnik {

    public Doktor(String ime, String prezime, Date datumRodjenja, String adresa, String email, String brojTelefona) {
        super(ime, prezime, datumRodjenja, adresa, email, brojTelefona);
    }
}
