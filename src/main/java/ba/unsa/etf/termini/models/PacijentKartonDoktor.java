package ba.unsa.etf.termini.models;

import lombok.Data;

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
    private Doktor doktor;

    @ManyToOne(cascade = {CascadeType.ALL},fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pacijent_id", nullable = false)
    private Pacijent pacijent;

    @OneToMany(targetEntity = Termin.class,
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER,
            mappedBy="pacijentKartonDoktor")
    private List<Termin> termini;

    public PacijentKartonDoktor(Doktor doktor, Pacijent pacijent) {
        this.doktor = doktor;
        this.pacijent = pacijent;
    }

    public PacijentKartonDoktor() {

    }
}
