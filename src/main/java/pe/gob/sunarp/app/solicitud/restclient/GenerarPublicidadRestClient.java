package pe.gob.sunarp.app.solicitud.restclient;

import pe.gob.sunarp.app.solicitud.bean.GenerarPublicidadBean;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;
import pe.gob.sunarp.app.solicitud.response.GuardarCajaResponse;

public interface GenerarPublicidadRestClient {
    GuardarCajaResponse generaPublicidad(GenerarPublicidadBean generarPublicidadBean) throws ExceptionBean;
}
