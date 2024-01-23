package pe.gob.sunarp.app.solicitud.restclient.impl;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;
import pe.gob.sunarp.app.solicitud.request.TIVeInternoRequest;
import pe.gob.sunarp.app.solicitud.response.TIVeInternoResponse;
import pe.gob.sunarp.app.solicitud.response.TIVeInternoValidationResponse;
import pe.gob.sunarp.app.solicitud.restclient.TIVeRestInternoClient;


@Slf4j
@Component
public class TIVeRestInternoClientImpl implements TIVeRestInternoClient{
	private static final Logger LOG = LoggerFactory.getLogger(TIVeRestInternoClientImpl.class);

    @Autowired
    private ApplicationContext context;

	HttpHeaders headers = null;
	ObjectMapper objectMapper = null;

	@PostConstruct
	public void init() {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		objectMapper = new ObjectMapper();
	}
	
	@Override
	public TIVeInternoResponse processTIVeInterno(TIVeInternoRequest tiveRequest) throws ExceptionBean {
		TIVeInternoResponse tiveInternoResponse = null;
		String jsonRequest = null;
		String result = "";
		String url = "";
		String urlValidation = "";

		LOG.info("TIVeRestInternoClient => processTIVeInterno -> año : " + tiveRequest.getAnioTitulo());		
		LOG.info("TIVeRestInternoClient => processTIVeInterno -> user: " + tiveRequest.getUser());

		try {
			url = context.getEnvironment().getProperty("tive.url");
			urlValidation = context.getEnvironment().getProperty("tive.validation.url"); 

			LOG.info("TIVeRestInternoClient => processTIVeInterno -> url : " + url);
			LOG.info("TIVeRestInternoClient => processTIVeInterno -> validation url : " + urlValidation);

			//----Verification code validation----
			if(tiveRequest.getUser().equals("app")) { //Titulos
				urlValidation+="/999";
			}
			else if(tiveRequest.getUser().equals("publicidad")) { //Publicidades
				urlValidation+="/189";
			}
			
			urlValidation+="-" + tiveRequest.getAnioTitulo() 
					+ "-" + tiveRequest.getNumeroTitulo()
					+ "-" + tiveRequest.getCodigoVerificacion()
					+ "-" + tiveRequest.getCodigoZona()
					+ "-" + tiveRequest.getCodigoOficina()
					+ "-0";
			
			LOG.info("TIVeRestInternoClient => validation -> complete validation url: " + urlValidation);
			
			RestTemplate restTemplate = new RestTemplate();
			TIVeInternoValidationResponse tiveValidation = restTemplate
					  .getForObject(urlValidation, TIVeInternoValidationResponse.class);
			
			LOG.info("TIVeRestInternoClient => validation -> codigoRespuesta: " + tiveValidation.getCodigoRespuesta());
			LOG.info("TIVeRestInternoClient => validation -> descripcion: " + tiveValidation.getDescripcionRespuesta());
			
			if(!(tiveValidation.getCodigoRespuesta().equals("000"))) {
				return null;
			}			
			//------------------------------------
			
			jsonRequest = objectMapper.writeValueAsString(tiveRequest);
			HttpEntity<String> entity = new HttpEntity<String>(jsonRequest, headers);

			result = restTemplate.postForObject(url, entity, String.class);

			tiveInternoResponse = objectMapper.readValue(result, TIVeInternoResponse.class);
		} catch (Exception e) {
			log.error("TIVeRestInternoClient => processTIVeInterno -> Exception : "
					+ e.getMessage());
			// Se mantiene lógica inicial, no se realiza: throw exceptionProperties.getNotFound();
		}

		return tiveInternoResponse;
	}
}
