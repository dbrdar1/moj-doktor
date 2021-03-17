package ba.unsa.etf.doktordetalji.controllers;

import ba.unsa.etf.doktordetalji.models.Doktor;
import ba.unsa.etf.doktordetalji.services.DoktorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@AllArgsConstructor
@RestController
public class DoktorController {

    private final DoktorService doktorService;

    @GetMapping("/getDoktori")
    public @ResponseBody
    Integer getDoktori(){
        List<Doktor> list = doktorService.getDoktori();
        return  list.size();
    }
}
