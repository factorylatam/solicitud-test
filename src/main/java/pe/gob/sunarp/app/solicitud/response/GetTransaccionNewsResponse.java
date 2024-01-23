package pe.gob.sunarp.app.solicitud.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTransaccionNewsResponse {
    private String fecHor;
    private String strBusq;
    private String estado;
    private long solicitudId;
}
