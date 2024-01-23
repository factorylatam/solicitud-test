package pe.gob.sunarp.app.solicitud.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AbonoBean {
    private long abonoId;
    private String oficRegId;
    private String regPubId;
    private String tipoAbono;
    private BigDecimal medioId;
    private String tipoVent;
    private String tpoPagVent;
    private String usrCaja;
    private String tipoUsr;
    private BigDecimal monto;
    private long movimientoId;
    private String fgCierre;
    private String rcboAsoc;
    private long personaId;
    private LocalDateTime tsCrea;
    private LocalDateTime tsModi;
    private String estado;
//    private Set<PagoSolicitud> pagoSolicituds = new HashSet<PagoSolicitud>(0);
//    private Set<Comprobante> comprobantes = new HashSet<Comprobante>(0);
//    private MediosPago mediosPago;
//    private Movimiento movimiento;
}
