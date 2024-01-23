package pe.gob.sunarp.app.solicitud.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagarLiquidacionResponse {
    private long solicitudId;
    private String descripcion;
    private String tsCrea;
    private BigDecimal total;
    private String email;
    private String solicitante;
    private String tpoPago;
    private long pagoSolicitudId;
    private String numeroRecibo;
    private String numeroPublicidad;
    private String codVerificacion;

    private long secReciDetaAtenNac;
//    private String codCertificado;

    //bgr
//    private boolean bgr;
//    private String codDescarga;
//    private String codLibroOpc;
}
