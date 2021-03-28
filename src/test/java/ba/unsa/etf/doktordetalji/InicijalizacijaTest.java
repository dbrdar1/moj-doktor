package ba.unsa.etf.doktordetalji;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InicijalizacijaTest{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void inicijalizacijaTest() throws Exception {
        this.mockMvc.perform(get("/inicijalizacija-baze"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.poruka", is("Inicijalizacija baze zavrsena!")));
    }

}