package pe.gob.sunarp.app.solicitud.restclient;

import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;
import pe.gob.sunarp.app.solicitud.request.TIVeInternoRequest;
import pe.gob.sunarp.app.solicitud.response.TIVeInternoResponse;

public interface TIVeRestInternoClient {
	TIVeInternoResponse processTIVeInterno(TIVeInternoRequest tiveRequest) throws ExceptionBean ;

}
