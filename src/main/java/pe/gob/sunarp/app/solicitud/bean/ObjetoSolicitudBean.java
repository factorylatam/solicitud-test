package pe.gob.sunarp.app.solicitud.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ObjetoSolicitudBean {
    private String solicitudId;
    private String aaPubl;
    private String nuPubl;
    private String certificadoId;
    private String oficRegId;
    private String regPubId;

    //bgr
    private String cflagCD;
    private String ccodDescarga;
    private String cdeObs;
}
