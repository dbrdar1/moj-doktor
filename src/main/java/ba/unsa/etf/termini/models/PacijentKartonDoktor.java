package ba.unsa.etf.termini.models;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
public class PacijentKartonDoktor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL},fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doktor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Doktor doktor;

    @ManyToOne(cascade = {CascadeType.ALL},fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pacijent_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Pacijent pacijent;

    @OneToMany(targetEntity = Termin.class,
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER,
            mappedBy="pacijentKartonDoktor")
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private List<Termin> termini;

    public PacijentKartonDoktor(Doktor doktor, Pacijent pacijent) {
        this.doktor = doktor;
        this.pacijent = pacijent;
    }

    public PacijentKartonDoktor() {

    }
}
