package ba.unsa.etf.doktordetalji.controllers;

import ba.unsa.etf.doktordetalji.services.DoktorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class DoktorController {

    private final DoktorService doktorService;

}
