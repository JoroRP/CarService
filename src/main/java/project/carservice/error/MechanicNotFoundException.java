package project.carservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "Mechanic not found")
public class MechanicNotFoundException extends RuntimeException{
    public MechanicNotFoundException(String message) {
        super(message);
    }
}
