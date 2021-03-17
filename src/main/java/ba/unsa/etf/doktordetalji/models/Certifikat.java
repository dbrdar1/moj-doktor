package ba.unsa.etf.doktordetalji.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "certifikat")
public class Certifikat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String institucija;

    @NotNull
    private String naziv;

    private Date godinaIzdavanja;

    public Certifikat(String institucija, String naziv, Date godinaIzdavanja){
        this.institucija = institucija;
        this.naziv = naziv;
        this.godinaIzdavanja = godinaIzdavanja;
    }

    @ManyToOne
    private Doktor doktor;

}
