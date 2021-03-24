package ba.unsa.etf.chatmicroservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DBObjectNotFoundException extends RuntimeException {

    public DBObjectNotFoundException(String message) {
        super(message);
    }
}
