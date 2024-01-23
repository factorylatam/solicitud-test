package pe.gob.sunarp.app.solicitud.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PagoSolicitudBean {
    private long solicitudId;
    private long abonoId;
    private String tpoPago;
    private BigDecimal monto;
    private LocalDateTime tsCrea;
    private LocalDateTime tsModi;
    private String usrCrea;
    private String usrModi;
    private String cpasolFlg;
}
