package pe.gob.sunarp.app.solicitud.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BoletaInformativaResponse  implements Serializable {
    private byte[] boleta;
}
