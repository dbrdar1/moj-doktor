package ba.unsa.etf.doktordetalji.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "certifikat")
public class Certifikat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String institucija;

    @NotBlank
    private String naziv;

    private Integer godinaIzdavanja;

    public Certifikat(String institucija, String naziv, Integer godinaIzdavanja){
        this.institucija = institucija;
        this.naziv = naziv;
        this.godinaIzdavanja = godinaIzdavanja;
    }

    @ManyToOne
    private Doktor doktor;

}
