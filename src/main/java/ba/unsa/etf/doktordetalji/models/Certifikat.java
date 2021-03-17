package ba.unsa.etf.doktordetalji.models;

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

    private String institucija;

    private String naziv;

    private Date godinaPocetka;

}
