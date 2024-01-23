package pe.gob.sunarp.app.solicitud.repository;

import java.util.List;

import pe.gob.sunarp.app.solicitud.bean.TIVeBean;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;

public interface TIVeDao {
	List<TIVeBean> getTIVeUserHistory(Long userId)  throws ExceptionBean ;
}
