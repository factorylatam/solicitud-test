package pe.gob.sunarp.app.solicitud.rest;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.gob.sunarp.app.solicitud.bean.TIVeBean;
import pe.gob.sunarp.app.solicitud.config.Auth;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;
import pe.gob.sunarp.app.solicitud.service.SecurityTokenService;
import pe.gob.sunarp.app.solicitud.service.TiveService;


@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tive")
public class TiveRest {
	private static final Logger LOG = LoggerFactory.getLogger(TiveRest.class);
	
    @Autowired
    private Auth auth;

    @Autowired
    private SecurityTokenService securityTokenService;

    @Autowired 
	private TiveService tiveService;


	@GetMapping(value = "/consulta/historia")
	public ResponseEntity <List<TIVeBean>> getTIVeHistory() throws ExceptionBean {		
    	securityTokenService.validateToken(auth.usuario().getJti());
		LOG.info("usuario = " + auth.usuario().getUserName());
		
		return new ResponseEntity<>(tiveService.getTIVeUserHistory(auth.usuario().getJti()), HttpStatus.OK);
	}
	/*
	@GetMapping(value="/tive_save_history")	
	public @ResponseBody TIVeSearchResponseBean saveOnTIVeHistory(String userId, TIVeBean tiveBean, @RequestHeader Map<String, String> headers) throws Exception {
    	securityTokenService.validateToken(auth.usuario().getJti());
		LOG.info("usuario = " + auth.usuario().getUserName());
		
		if(userId == null || tiveBean == null) {
			return new TIVeSearchResponseBean("888", "Debe enviar todos los campos", null);
		}

		HeaderBean hbean = getHeaders(headers);
		if (!validaHeader(hbean)){
			return new TIVeSearchResponseBean("403","No cuenta con permisos", null);
		}		
		
		int code = iTiVeSearchService.saveTIVeOnHistory(userId, tiveBean);
		
		if(code == 0) {
			return new TIVeSearchResponseBean("000", "Registro ya existente", null); 
		}
		else if (code == 1) {
			return new TIVeSearchResponseBean("000", "Guardado exitosamente", null); 
		}
		else if (code == 2) {
			return new TIVeSearchResponseBean("888", "Faltan datos sobre la TIVe", null); 
		}
		
		return null;
	}
	
	
	//02/10/2020	lzambrano	point de solicitud del documento PDF TIVe
	@GetMapping(value = "/tive_search_pdf")
	public @ResponseBody TIVeSearchResponseBean getTIVePDF(HttpServletRequest request, @RequestHeader Map<String, String> headers, TIVeSearchRequestBean tiveRequest){		
    	securityTokenService.validateToken(auth.usuario().getJti());
		LOG.info("usuario = " + auth.usuario().getUserName());
		
		HeaderBean hbean = getHeaders(headers);
		if (!validaHeader(hbean)){
			return new TIVeSearchResponseBean("403","No cuenta con permisos", null);
		}
		
		System.out.println("Home controller => getTIVePDF");
		
		return iTiVeSearchService.getTIVeDocument(tiveRequest);
	}
*/
}
