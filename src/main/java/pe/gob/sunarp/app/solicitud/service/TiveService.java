package pe.gob.sunarp.app.solicitud.service;
import java.util.List;

import pe.gob.sunarp.app.solicitud.bean.TIVeBean;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;


public interface TiveService {
	
	List<TIVeBean> getTIVeUserHistory(String jti)  throws ExceptionBean;

}
