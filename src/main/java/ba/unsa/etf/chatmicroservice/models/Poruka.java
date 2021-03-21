package ba.unsa.etf.chatmicroservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @NotBlank
    private String sadrzaj;

    private Integer procitana;

    private Integer jeLiOdPrvogKorisnika;

    private Date datumPoruke;

    @NotBlank
    private String vrijemePoruke;

    @ManyToOne
    @JoinColumn(name = "razgovorId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Razgovor razgovor;

    public Poruka(String sadrzaj, Integer procitana, Integer jeLiOdPrvogKorisnika, Date datumPoruke, String vrijemePoruke, Razgovor razgovor) {
        this.sadrzaj = sadrzaj;
        this.procitana = procitana;
        this.jeLiOdPrvogKorisnika = jeLiOdPrvogKorisnika;
        this.datumPoruke = datumPoruke;
        this.vrijemePoruke = vrijemePoruke;
        this.razgovor = razgovor;
    }
}
