package pe.gob.sunarp.app.solicitud.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ComprobanteBean {
    private long comprobanteId;
    private long abonoId;
    private BigDecimal monto;
    private String estado;
}
