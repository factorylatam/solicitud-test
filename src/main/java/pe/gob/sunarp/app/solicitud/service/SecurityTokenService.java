package pe.gob.sunarp.app.solicitud.service;

import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;

public interface SecurityTokenService {

    void validateToken(String codigo) throws ExceptionBean;
}
