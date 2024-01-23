package pe.gob.sunarp.app.solicitud.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GetSolicitudResponse implements Serializable {

    private String solicitudId;
    private String userKeyId;
    private String ncopAdic;
    private String nsolMonLiq;
    private String tsDesis;
    private String tsMaxReing;

    //flags
    private FlagResponse flags;

    //Estado
    private EstadoResponse estado;

    //Certificado
    private CertificadoResponse certificado;

    //solicitante
    private SolicitanteResponse solicitante;

    //Destinatario
    private DestinatarioResponse destinatario;

    //pago
    private PagoResponse pago;

    private String sistema;


}
