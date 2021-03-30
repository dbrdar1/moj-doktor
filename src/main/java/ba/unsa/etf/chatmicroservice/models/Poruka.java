package ba.unsa.etf.chatmicroservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "poruka")
public class Poruka {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Poruka mora imati sadrzaj")
    private String sadrzaj;

    @Min(value = 0, message = "Vrijednost Procitana moze biti 0 ili 1")
    @Max(value = 1, message = "Vrijednost Procitana moze biti 0 ili 1")
    private Integer procitana;

    @NotNull
    private Date datum;

    @NotBlank
    private String vrijeme;

    @ManyToOne
    @JoinColumn(name = "posiljalac_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Korisnik posiljalac;

    @ManyToOne
    @JoinColumn(name = "primalac_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Korisnik primalac;

    public Poruka(String sadrzaj, Integer procitana, Date datum, String vrijeme, Korisnik posiljalac, Korisnik primalac) {
        this.sadrzaj = sadrzaj;
        this.procitana = procitana;
        this.datum = datum;
        this.vrijeme = vrijeme;
        this.posiljalac = posiljalac;
        this.primalac = primalac;
    }
}
