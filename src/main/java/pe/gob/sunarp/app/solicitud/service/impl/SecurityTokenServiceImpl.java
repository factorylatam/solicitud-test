package pe.gob.sunarp.app.solicitud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gob.sunarp.app.solicitud.cache.RedisService;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;
import pe.gob.sunarp.app.solicitud.exception.ExceptionProperties;
import pe.gob.sunarp.app.solicitud.service.SecurityTokenService;

@Service
public class SecurityTokenServiceImpl implements SecurityTokenService {

    @Autowired
    private RedisService redis;

    @Autowired
    private ExceptionProperties exceptionProperties;

    @Override
    public void validateToken(String codigo) throws ExceptionBean {
        Integer valor = redis.getTokenCache(codigo);

        if (valor == null){
            throw exceptionProperties.getUnauthorized();
        }
    }
}
