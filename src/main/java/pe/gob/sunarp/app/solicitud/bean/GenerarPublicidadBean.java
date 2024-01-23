package pe.gob.sunarp.app.solicitud.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GenerarPublicidadBean {
    private String coZonaRegi;
    private String cantRecibos;
    private String codMoneda;
    private String tipDocumento;
    private String numDocumento;
    private String coOficRegi;
    private String codCaja;
    private String coLocaAten;
    private String solicitudId;
    private String codVerificacion;
    private String secuIdRecibo;
}
