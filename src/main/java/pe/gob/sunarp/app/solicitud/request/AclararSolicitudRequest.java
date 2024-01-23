package pe.gob.sunarp.app.solicitud.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AclararSolicitudRequest  implements Serializable {
    private String solicitudId;
    private String detalle;
    private String usuario;
}
