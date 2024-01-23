package pe.gob.sunarp.app.solicitud.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EstadoResponse  implements Serializable {
    private String estado;
    private String codEstado;
}
