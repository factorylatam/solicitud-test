package pe.gob.sunarp.app.solicitud.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ExceptionResponse {
    private String code;
    private String description;
    private HttpStatus category;
    private String component;
}
