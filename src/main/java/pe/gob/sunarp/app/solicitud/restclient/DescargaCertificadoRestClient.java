package pe.gob.sunarp.app.solicitud.restclient;

import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;
import pe.gob.sunarp.app.solicitud.request.DescargaCertificadoRequest;
import pe.gob.sunarp.app.solicitud.response.DescargarCertificadoResponse;
import pe.gob.sunarp.app.solicitud.response.SolicitudDocumentoResponse;

public interface DescargaCertificadoRestClient {
    DescargarCertificadoResponse descargarCertificado(DescargaCertificadoRequest request) throws ExceptionBean;
}
