package ba.unsa.etf.chatmicroservice.projections;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface PorukaProjection {

    Long getId();
    String getSadrzaj();
    Integer getProcitana();
    Date getDatum();
    String getVrijeme();

    @Value("#{target.getPosiljalac().getId()}")
    Long getPosiljalacId();

    @Value("#{target.getPrimalac().getId()}")
    Long getPrimalacId();
}
