package pe.gob.sunarp.app.solicitud.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MovimientoBean {
    private LocalDateTime fecHor;
    private String fgAsig;
    private BigDecimal montoFin;
    private String tpoMov;
    private long lineaPrepago;
}
