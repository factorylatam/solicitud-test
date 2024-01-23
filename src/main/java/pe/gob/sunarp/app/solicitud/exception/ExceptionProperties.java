package pe.gob.sunarp.app.solicitud.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "appsunarp.error")
@Component
@Getter
@Setter
public class ExceptionProperties {

    private ExceptionBean unauthorized;
    private ExceptionBean notFound;
    private ExceptionBean reEntryFailed;
    private ExceptionBean notFoundLogin;
    private ExceptionBean downloadFailed;
    private ExceptionBean compileFailed;

}
