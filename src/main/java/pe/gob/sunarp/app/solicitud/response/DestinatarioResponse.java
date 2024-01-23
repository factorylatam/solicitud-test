package pe.gob.sunarp.app.solicitud.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DestinatarioResponse  implements Serializable {
    private String tpoEnvio;
    private String tpoEnvioDesc;
    private String oficina;
    private String distrito;
    private String direccion;
    private String codPostal;
    private String departamento;
    private String provincia;
    private String nombre;
}
