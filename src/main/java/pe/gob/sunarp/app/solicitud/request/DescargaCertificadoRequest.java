package pe.gob.sunarp.app.solicitud.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DescargaCertificadoRequest {
    private String codigoZona;
    private String codigoOficina;
    private String anioPubl;
    private String numPubl;
    private String codigoServicio;
    private String codigoTipoEsquela;
}
