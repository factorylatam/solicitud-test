package pe.gob.sunarp.app.solicitud.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.sunarp.app.seguridad.bean.UsuarioJtiBean;
import pe.gob.sunarp.app.solicitud.bean.TIVeBean;
import pe.gob.sunarp.app.solicitud.cache.RedisService;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;
import pe.gob.sunarp.app.solicitud.exception.ExceptionProperties;
import pe.gob.sunarp.app.solicitud.repository.TIVeDao;
import pe.gob.sunarp.app.solicitud.request.TIVeInternoRequest;
import pe.gob.sunarp.app.solicitud.response.TIVeInternoResponse;
import pe.gob.sunarp.app.solicitud.restclient.TIVeRestInternoClient;
import pe.gob.sunarp.app.solicitud.service.TiveService;

@Service
public class TiveServiceImpl implements TiveService{
	private static final Logger LOG = LoggerFactory.getLogger(TiveServiceImpl.class);
	
    @Autowired
    private ExceptionProperties exceptionProperties;
    
	@Autowired 
	private RedisService redisImpl;
	
	@Autowired
	private TIVeDao tiveDao;
	
	@Autowired
	private TIVeRestInternoClient tiveRestInterno;

	private TIVeInternoRequest objInRestWithDocument(TIVeBean r){
		return new TIVeInternoRequest(
				r.getCodigoZona(),
				r.getCodigoOficina(),
				r.getAnioTitulo(),
				r.getNumeroTitulo(),
				r.getNumeroPlaca(),
				r.getCodigoVerificacion(),
				r.getTipo()
				);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TIVeBean> getTIVeUserHistory(String jti)  throws ExceptionBean {
		// Se obtiene los datos del usuario desde Redis:
		UsuarioJtiBean usuario = redisImpl.getUsuarioJti(jti);
		
		if(usuario != null) {
			List<TIVeBean> response = new ArrayList(); //= new TIVeListSearchResponseBean();
			
			List<TIVeBean> tiveList = tiveDao.getTIVeUserHistory(usuario.getIdUser());
			
			for(TIVeBean tiveBean : tiveList) {
				
				LOG.info("getTIVeUserHistory => -----------Datos de TIVe-----------");
				LOG.info("getTIVeUserHistory => Codigo de oficina: " + tiveBean.getCodigoOficina());
				LOG.info("getTIVeUserHistory => Codigo de zona: " + tiveBean.getCodigoZona());
				LOG.info("getTIVeUserHistory => Año de titulo: " + tiveBean.getAnioTitulo());
				LOG.info("getTIVeUserHistory => Numero de titulo: " + tiveBean.getNumeroTitulo());
				LOG.info("getTIVeUserHistory => Numero de placa: " + tiveBean.getNumeroPlaca());
				LOG.info("getTIVeUserHistory => Codigo de verificacion: " + tiveBean.getCodigoVerificacion());
				LOG.info("getTIVeUserHistory => Tipo: " + tiveBean.getTipo());
				
				TIVeInternoResponse tiveInterno = tiveRestInterno.processTIVeInterno(objInRestWithDocument(tiveBean));
				
				LOG.info("getTIVeUserHistory => tiveInterno code: " + tiveInterno.getCode());
				LOG.info("getTIVeUserHistory => tiveInterno message: " + tiveInterno.getErrorMessage());
				
				if(tiveInterno != null){
					tiveBean.setDocumento(tiveInterno.getDocumento());
					// response.addTiveBean(tiveBean);
					response.add(tiveBean);
				}
			}
			
			// response.setCode("000");
			
			if(!tiveList.isEmpty()) {
				LOG.info("== tiveList.size() > 0 ==");
				//response.setErrorMessage("-");
			}
			else {
				LOG.info("== tiveList.size() = 0 == No cuenta con ningun archivo en historico");
				//response.setErrorMessage("No cuenta con ningun archivo en historico");
				throw exceptionProperties.getNotFound();
			}
			
			return response;

		}
		else {
			// Datos del usuario no encontrado en Redis
			throw exceptionProperties.getNotFoundLogin();
		}
	}
}
