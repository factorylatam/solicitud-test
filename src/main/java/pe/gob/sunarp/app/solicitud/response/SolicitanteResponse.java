package pe.gob.sunarp.app.solicitud.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SolicitanteResponse  implements Serializable {
    private String numDoc;
    private String tpoDoc;
    private String nombre;
}
