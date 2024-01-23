package pe.gob.sunarp.app.solicitud.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RespuestaTxcajaBean implements Serializable {
    private String codZonaRegistral;
    private String aaRecibo;
    private String codCaja;
    private String numeroRecibo;
    private String aaPublicidad;
    private String numeroPublicidad;
}
