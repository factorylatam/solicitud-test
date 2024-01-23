package pe.gob.sunarp.app.solicitud.request;

import lombok.Getter;
import lombok.Setter;
import pe.gob.sunarp.app.solicitud.bean.VisanetResponseBean;

@Getter
@Setter
public class PagarLiquidacionRequest {
    private long solicitudId;
    private String usuario;
    private String detalle;
    private String monto;
    private VisanetResponseBean visanetResponse;

}
