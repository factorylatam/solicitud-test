package pe.gob.sunarp.app.solicitud.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class SgmtSolicitudBean implements Serializable {

    private String solicitudId;
    private String estadoInicial;
    private String estadoFinal;
    private LocalDateTime tsMovimiento;
    private String usrMovimiento;
    private String flgrev;
    private String comentario;
    private LocalDateTime tsModi;
    private String usrModi;
    private String solicitante;
}
