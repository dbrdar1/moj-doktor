package ba.unsa.etf.termini.models;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "pacijentKartonDoktor")
public class PacijentKartonDoktor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Doktor doktor;

    @ManyToOne
    private Pacijent pacijent;

    @OneToMany(mappedBy = "pacijentKartonDoktor", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Termin> termini = new ArrayList<>();

    public PacijentKartonDoktor(Doktor doktor, Pacijent pacijent) {
        this.doktor = doktor;
        this.pacijent = pacijent;
    }

    public PacijentKartonDoktor() {

    }
}
