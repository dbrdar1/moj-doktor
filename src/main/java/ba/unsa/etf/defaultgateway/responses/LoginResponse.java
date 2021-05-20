package ba.unsa.etf.defaultgateway.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private final String vrstaTokena = "Bearer";
    private String token;
   // private String uloga;
   // private Long id;
}
