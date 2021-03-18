package ba.unsa.etf.termini.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    /*
    public List<KartonPacijent> dajPacijente() {
        return pacijenti;
    }

    public void postaviPacijente(List<KartonPacijent> pacijenti) {
        this.pacijenti = pacijenti;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "pacijent_karton_doktor",
            joinColumns = @JoinColumn(name = "pacijent_JMBG"),
            inverseJoinColumns = @JoinColumn(name = "doktor_id"))
    private List<KartonPacijent> pacijenti;

    public void dodajPacijentaDoktoru(KartonPacijent pacijent) {
        pacijenti.add(pacijent);
        List<Doktor> doktoriNovi = pacijent.dajDoktore();
        doktoriNovi.add(this);
        pacijent.postaviDoktore(doktoriNovi);
    }

    public void izbaciPacijenta(KartonPacijent pacijent) {
        pacijenti.remove(pacijent);
        List<Doktor> doktoriNovi = pacijent.dajDoktore();
        doktoriNovi.remove(this);
        pacijent.postaviDoktore(doktoriNovi);
    }*/
}
