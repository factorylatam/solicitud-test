package pe.gob.sunarp.app.solicitud.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExceptionBean extends Exception{

    private String code;
    private String description;
    private HttpStatus category;
    private String component;

}
