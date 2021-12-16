package project.carservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "Password not correct!")
public class PasswordNotCorrectException extends RuntimeException{

}
