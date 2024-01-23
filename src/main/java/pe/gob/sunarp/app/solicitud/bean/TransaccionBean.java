package pe.gob.sunarp.app.solicitud.bean;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransaccionBean {
//    private String tipo;
    private long transId;
    private String fecHor;
    private int servicioId;
    private long cuentaId;
    private String strBusq;
    private BigDecimal costo;
//    private long key;
    private byte[] blobJson;
    private long solicitudId;
    private String aaPubl;
    private String nuPubl;
    private String estado;
}
