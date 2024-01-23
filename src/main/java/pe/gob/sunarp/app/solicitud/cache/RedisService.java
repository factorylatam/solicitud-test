package pe.gob.sunarp.app.solicitud.cache;


import pe.gob.sunarp.app.seguridad.bean.UsuarioJtiBean;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;

public interface RedisService {

    Integer getTokenCache(String jti) throws ExceptionBean;
    
    UsuarioJtiBean getUsuarioJti(String jti) throws ExceptionBean;
    
}
