package ba.unsa.etf.doktordetalji.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "edukacija")
public class Edukacija {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Naziv institucije mora biti unesen.")
    @Size(min = 2, message = "Naziv institucije mora biti dug bar dva znaka.")
    private String institucija;

    @NotBlank(message = "Odsjek mora biti unesen.")
    @Size(min = 2, message = "Naziv odsjeka mora biti dug bar dva znaka.")
    private String odsjek;

    @NotBlank(message = "Stepen mora biti unesen.")
    @Size(min = 2, message = "Naziv stepena mora biti dug bar dva znaka.")
    private String stepen;

    @Min(1900)
    @NotBlank(message = "Godina pocetka mora biti unesena.")
    private Integer godinaPocetka;

    @Min(1900)
    @NotBlank(message = "Godina zavrsetka mora biti unesena.")
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
