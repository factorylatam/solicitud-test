package pe.gob.sunarp.app.solicitud.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ReingresoRequestBean implements Serializable {
    private String coZonaRegi;
    private String coOficRegi;
    private String anioPublicidad;
    private long numPublicidad;
    private String codTipReing;
    private String desTipReing;
    private byte[] igReing;
}
