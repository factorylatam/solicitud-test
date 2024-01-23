package pe.gob.sunarp.app.solicitud.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DesistirSolicitudRequest implements Serializable {
    private String solicitudId;
    private String usuario;
}
