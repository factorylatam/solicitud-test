package pe.gob.sunarp.app.solicitud.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FlagResponse  implements Serializable {
    private String flgObs;
    private String flgEstado;
    private String flgLiq;
    private String flgTipoLiq;
    private String flgDesis;
    private String flgDeneg;
    private String flgAban;
    private String flgAclar;
}
