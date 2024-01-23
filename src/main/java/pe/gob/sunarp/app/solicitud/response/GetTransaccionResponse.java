package pe.gob.sunarp.app.solicitud.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GetTransaccionResponse {
    private String tipo;
    private long transId;
    private String fecHor;
    private int servicioId;
    private long cuentaId;
    private String strBusq;
    private BigDecimal costo;
    private long key;
    private String aaPubl;
    private String nuPubl;
    private long solicitudId;
}
