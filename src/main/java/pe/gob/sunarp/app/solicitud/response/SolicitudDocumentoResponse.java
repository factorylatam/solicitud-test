package pe.gob.sunarp.app.solicitud.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudDocumentoResponse{
    private byte[] documento;
    private String fileName;
}
