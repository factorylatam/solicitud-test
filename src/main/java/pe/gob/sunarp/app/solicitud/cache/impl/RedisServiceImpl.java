package pe.gob.sunarp.app.solicitud.cache.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import pe.gob.sunarp.app.seguridad.bean.UsuarioJtiBean;
import pe.gob.sunarp.app.solicitud.cache.RedisService;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
	private static final Logger LOG = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Override
    @Cacheable(value = "TOKENS-APP", key = "#jti")
    public Integer getTokenCache(String jti) throws ExceptionBean {
    	LOG.info("jti = " + jti);
        return null;
    }
    

	@Override
	@Cacheable(value = "USUARIOS-APP", key = "#jti")
	public UsuarioJtiBean getUsuarioJti(String jti) throws ExceptionBean {
		LOG.info("jti (no encontrado) = " + jti);
		return null;
	}
    
    
}
