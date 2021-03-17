package ba.unsa.etf.doktordetalji.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "edukacija")
public class Edukacija {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String institucija;

    @NotNull
    private String odsjek;

    @NotNull
    private String stepen;

    @NotNull
    @Min(1900)
    @Past
    private Integer godinaPocetka;

    @NotNull
    @Min(1900)
    @Past
    private Integer godinaZavrsetka;

    @NotNull
    private String grad;

    @NotNull
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
