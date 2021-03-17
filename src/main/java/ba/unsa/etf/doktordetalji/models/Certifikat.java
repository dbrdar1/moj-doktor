package ba.unsa.etf.doktordetalji.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "certifikat")
public class Certifikat {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String institucija;

    @NonNull
    private String naziv;

    @NonNull
    @Min(1900)
    @PastOrPresent
    private Date godinaIzdavanja;

    public Certifikat(String institucija, String naziv, Date godinaIzdavanja){
        this.institucija = institucija;
        this.naziv = naziv;
        this.godinaIzdavanja = godinaIzdavanja;
    }

    @ManyToOne
    private Doktor doktor;

}
