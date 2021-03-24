package ba.unsa.etf.chatmicroservice.advices;

import ba.unsa.etf.chatmicroservice.exceptions.DBObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DBObjectNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(DBObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String employeeNotFoundHandler(DBObjectNotFoundException e) {
        return e.getMessage();
    }
}
