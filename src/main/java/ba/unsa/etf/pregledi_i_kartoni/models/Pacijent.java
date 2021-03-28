package ba.unsa.etf.pregledi_i_kartoni.models;

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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pacijent")
public class Pacijent extends Korisnik{

    private String spol;
    private double visina;
    private double tezina;
    private String krvnaGrupa;
    private String hronicneBolesti;
    private String hronicnaTerapija;

    /*

    @OneToMany(mappedBy = "pacijent", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PacijentDoktor> vezeSaDoktorima = new ArrayList<>();

    */

    public Pacijent(String ime, String prezime, Date datumRodjenja, String adresa,
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

    public Pacijent(String ime, String prezime, Date datumRodjenja, String adresa,
                    String brojTelefona, String mail) {
        super(ime, prezime, datumRodjenja, adresa, brojTelefona, mail);
    }

}

