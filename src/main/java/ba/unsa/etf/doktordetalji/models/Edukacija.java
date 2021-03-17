package ba.unsa.etf.doktordetalji.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "edukacija")
public class Edukacija {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String institucija;

    private String odsjek;

    private String stepen;

    private Integer godinaPocetka;

    private Integer godinaZavrsetka;

    private String grad;

    private String drzava;

    public Edukacija(String institucija, String odsjek, String stepen, Integer godinaPocetka, Integer godinaZavrsetka, String grad, String drzava){
        this.institucija = institucija;
        this.odsjek = odsjek;
        this. stepen = stepen;
        this.godinaPocetka = godinaPocetka;
        this.godinaZavrsetka = godinaZavrsetka;
        this.grad = grad;
        this.drzava = drzava;
    }

    @ManyToOne
    private Doktor doktor;

}
