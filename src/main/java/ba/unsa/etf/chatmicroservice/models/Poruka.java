package ba.unsa.etf.chatmicroservice.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Poruka {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sadrzaj;

    private Integer procitana;

    private Integer jeLiOdPrvogKorisnika;

    private Date datumPoruke;

    private String vrijemePoruke;

    @ManyToOne(cascade = CascadeType.ALL)
    private Razgovor razgovor;

    public Poruka(String sadrzaj, Integer procitana, Integer jeLiOdPrvogKorisnika, Date datumPoruke, String vrijemePoruke, Razgovor razgovor) {
        this.sadrzaj = sadrzaj;
        this.procitana = procitana;
        this.jeLiOdPrvogKorisnika = jeLiOdPrvogKorisnika;
        this.datumPoruke = datumPoruke;
        this.vrijemePoruke = vrijemePoruke;
        this.razgovor = razgovor;
    }

    public Poruka() { }
}
