package pe.gob.sunarp.app.solicitud.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PagoResponse  implements Serializable {
    private String monto;
    private String fecha;
    private String tpoPago;
    private String mayorDerecho;
    private String codTpoEsquela;
}
