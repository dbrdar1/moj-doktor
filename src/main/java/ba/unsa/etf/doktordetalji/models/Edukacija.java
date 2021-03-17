package ba.unsa.etf.doktordetalji.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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

}
