package pe.gob.sunarp.app.solicitud.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SolicitudBean  implements Serializable {
    private String solicitudId;
    private String estado;
    private String userKeyId;
    private String csolflgaban;
    private String csolFlgdeneg;
    private String csolFlgliq;
    private String csolEstado;
    private String csolFlgobs;
    private String csolFlgrec;
    private String csolFlgAclar;
    private String csolFlgDesis;
    private String csolFlgTipoLiq;
    private String nsolMonLiq;
    private String ncopAdic;
    private LocalDateTime tsModi;
    private String usrModi;
    private LocalDateTime tsDesis;
    private String tsMaxReing;

}
