package pe.gob.sunarp.app.solicitud.response;

import lombok.Getter;
import lombok.Setter;
import pe.gob.sunarp.app.solicitud.bean.GenericResponseBean;

@Getter
@Setter
public class DescargarCertificadoResponse  extends GenericResponseBean {
    private byte[] documento;
    private String fileName;
}
