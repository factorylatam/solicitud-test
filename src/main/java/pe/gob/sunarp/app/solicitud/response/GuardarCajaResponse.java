package pe.gob.sunarp.app.solicitud.response;

import lombok.Getter;
import lombok.Setter;
import pe.gob.sunarp.app.solicitud.bean.GenericResponseBean;
import pe.gob.sunarp.app.solicitud.bean.RespuestaTxcajaBean;

@Getter
@Setter
public class GuardarCajaResponse extends GenericResponseBean {
    private RespuestaTxcajaBean respuestaTxcaja;
}
