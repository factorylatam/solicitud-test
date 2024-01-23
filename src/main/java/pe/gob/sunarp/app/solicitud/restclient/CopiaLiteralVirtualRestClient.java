package pe.gob.sunarp.app.solicitud.restclient;

import pe.gob.sunarp.app.solicitud.bean.GenericResponseBean;
import pe.gob.sunarp.app.solicitud.bean.ReingresoRequestBean;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;

public interface CopiaLiteralVirtualRestClient {

    GenericResponseBean generarReingresoPublicidad(ReingresoRequestBean reingresoRequestBean) throws ExceptionBean;
}
