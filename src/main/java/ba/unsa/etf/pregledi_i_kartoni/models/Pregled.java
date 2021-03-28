package ba.unsa.etf.pregledi_i_kartoni.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "pregled")
public class Pregled {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String simptomi;

    private String fizikalniPregled;

    private String dijagnoza;

    private String tretman;

    private String komentar;

    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Termin termin;

    public Pregled(String simptomi, String fizikalniPregled, String dijagnoza, String tretman, String komentar, Termin termin) {
        this.simptomi = simptomi;
        this. fizikalniPregled = fizikalniPregled;
        this.dijagnoza = dijagnoza;
        this.tretman = tretman;
        this.komentar = komentar;
        this.termin = termin;
    }

}
