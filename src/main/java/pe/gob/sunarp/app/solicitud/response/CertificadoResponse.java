package pe.gob.sunarp.app.solicitud.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CertificadoResponse  implements Serializable {
    private String certificadoId;
    private String tpoCertificado;
    private String aaPubl;
    private String nuPubl;
    private String oficina;
    private String codOficina;
    private String codZona;
    private String numPartida;
    private String asiento;
    private String annoTitulo;
    private String numTitulo;
    private String numPaginas;
    private String acto;
    private String tpoPers;
    private String nombrePers;
    private String areaRegistral;
}
