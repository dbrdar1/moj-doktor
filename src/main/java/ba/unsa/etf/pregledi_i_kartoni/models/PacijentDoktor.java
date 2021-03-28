package ba.unsa.etf.pregledi_i_kartoni.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pacijent_doktor")
public class PacijentDoktor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*
    @OneToMany(mappedBy = "pacijent_doktor", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Termin> termini = new ArrayList<>();
    */

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Doktor doktor;

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Pacijent pacijent;

    public PacijentDoktor(Doktor doktor, Pacijent pacijent) {

        this.doktor = doktor;
        this.pacijent = pacijent;

    }


}
