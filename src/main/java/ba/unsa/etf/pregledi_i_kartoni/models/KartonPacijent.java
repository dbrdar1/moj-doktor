package ba.unsa.etf.pregledi_i_kartoni.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "karton_pacijent")
public class KartonPacijent extends Korisnik{

    private String spol;
    private double visina;
    private double tezina;
    private String krvnaGrupa;
    private String hronicneBolesti;
    private String hronicnaTerapija;

    public KartonPacijent(String ime, String prezime, Date datumRodjenja, String adresa,
                          String brojTelefona, String mail, String spol, double visina,
                          double tezina, String krvnaGrupa, String hronicneBolesti,
                          String hronicnaTerapija) {

        super(ime, prezime, datumRodjenja, adresa, brojTelefona, mail);
        this.spol = spol;
        this.visina = visina;
        this.tezina = tezina;
        this.krvnaGrupa = krvnaGrupa;
        this.hronicneBolesti = hronicneBolesti;
        this.hronicnaTerapija = hronicnaTerapija;
    }

    public KartonPacijent(String ime, String prezime, Date datumRodjenja, String adresa,
                  String brojTelefona, String mail) {
        super(ime, prezime, datumRodjenja, adresa, brojTelefona, mail);
    }

}

