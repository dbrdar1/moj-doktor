package ba.unsa.etf.doktordetalji.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ocjena")
public class Ocjena {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer vrijednost;

    public Ocjena(Integer vrijednost){
        this.vrijednost = vrijednost;
    }

    @ManyToOne
    private Doktor doktor;
}
