package pe.gob.sunarp.app.solicitud.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pe.gob.sunarp.app.solicitud.config.Auth;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;
import pe.gob.sunarp.app.solicitud.request.AclararSolicitudRequest;
import pe.gob.sunarp.app.solicitud.request.DesistirSolicitudRequest;
import pe.gob.sunarp.app.solicitud.request.PagarLiquidacionRequest;
import pe.gob.sunarp.app.solicitud.request.SubsanarSolicitudRequest;
import pe.gob.sunarp.app.solicitud.response.BoletaInformativaResponse;
import pe.gob.sunarp.app.solicitud.response.GetSolicitudResponse;
import pe.gob.sunarp.app.solicitud.response.GetTransaccionNewsResponse;
import pe.gob.sunarp.app.solicitud.response.GetTransaccionResponse;
import pe.gob.sunarp.app.solicitud.response.PagarLiquidacionResponse;
import pe.gob.sunarp.app.solicitud.response.ProcesoResponse;
import pe.gob.sunarp.app.solicitud.response.SolicitudDocumentoResponse;
import pe.gob.sunarp.app.solicitud.service.SecurityTokenService;
import pe.gob.sunarp.app.solicitud.service.SolicitudService;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/solicitud")
public class SolicitudRest {
	private static final Logger LOG = LoggerFactory.getLogger(SolicitudRest.class);
	
    @Autowired
    private Auth auth;

    @Autowired
    private SecurityTokenService securityTokenService;

    @Autowired
    private SolicitudService solicitudService;
    
    @GetMapping(value = "/transaccion")
    public ResponseEntity<List<GetTransaccionResponse>> getTransactionFromUser() throws ExceptionBean {
    	securityTokenService.validateToken(auth.usuario().getJti());
		LOG.info("usuario = " + auth.usuario().getUserName());
		
        return new ResponseEntity<>(solicitudService.getTransactionFromUser(auth.usuario().getJti()), HttpStatus.OK);
    }

    @GetMapping(value = "/news/transaccion")
    public ResponseEntity<List<GetTransaccionNewsResponse>> getTransactionFromUserNews() throws ExceptionBean {
    	securityTokenService.validateToken(auth.usuario().getJti());
		LOG.info("usuario = " + auth.usuario().getUserName());
		
        return new ResponseEntity<>(solicitudService.getTransactionFromUserNews(auth.usuario().getJti()), HttpStatus.OK);
    }    
    
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetSolicitudResponse> getSolicitud(@PathVariable String id) throws ExceptionBean {
    	securityTokenService.validateToken(auth.usuario().getJti());
		LOG.info("usuario = " + auth.usuario().getUserName());
		
        return new ResponseEntity<>(solicitudService.getSolicitud(id), HttpStatus.OK);
    }

    @GetMapping(value = "/documento/{id}")
    public ResponseEntity<SolicitudDocumentoResponse> getSolicitudDocumento(@PathVariable String id) throws ExceptionBean {
        securityTokenService.validateToken(auth.usuario().getJti());
        LOG.info("usuario = " + auth.usuario().getUserName());

        return new ResponseEntity<>(solicitudService.getSolicitudDocumento(id), HttpStatus.OK);
    }

    @PostMapping(value = "/aclaramiento")
    public ResponseEntity<ProcesoResponse> aclararSolicitud(@RequestBody AclararSolicitudRequest aclararSolicitud) throws ExceptionBean {
    	securityTokenService.validateToken(auth.usuario().getJti());
		LOG.info("usuario = " + auth.usuario().getUserName());
		String usuaModi = aclararSolicitud.getUsuario().substring(0, Math.min(15, aclararSolicitud.getUsuario().length()));
		aclararSolicitud.setUsuario(usuaModi);
        return new ResponseEntity<>(solicitudService.aclararSolicitud(aclararSolicitud, auth.usuario().getJti()), HttpStatus.OK);
    }

    @PostMapping(value = "/subsanacion")
    public ResponseEntity<ProcesoResponse> subsanarSolicitud(@RequestBody SubsanarSolicitudRequest subsanarSolicitud) throws ExceptionBean {
    	securityTokenService.validateToken(auth.usuario().getJti());
		LOG.info("usuario = " + auth.usuario().getUserName());
		String usuaModi = subsanarSolicitud.getUsuario().substring(0, Math.min(15, subsanarSolicitud.getUsuario().length()));
		subsanarSolicitud.setUsuario(usuaModi);
		
        return new ResponseEntity<>(solicitudService.subsanarSolicitud(subsanarSolicitud, auth.usuario().getJti()), HttpStatus.OK);
    }

    @PostMapping(value = "/desistimiento")
    public ResponseEntity<ProcesoResponse> desistirSolicitud(@RequestBody DesistirSolicitudRequest desistirSolicitud) throws ExceptionBean {
    	securityTokenService.validateToken(auth.usuario().getJti());
		LOG.info("usuario = " + auth.usuario().getUserName());
		
		String usuaModi = desistirSolicitud.getUsuario().substring(0, Math.min(15, desistirSolicitud.getUsuario().length()));
		desistirSolicitud.setUsuario(usuaModi);
        return new ResponseEntity<>(solicitudService.desistirSolicitud(desistirSolicitud, auth.usuario().getJti()), HttpStatus.OK);
    }

    @PostMapping(value = "/pagar/liquidacion")
    public ResponseEntity<PagarLiquidacionResponse> pagarLiquidacion(@RequestBody PagarLiquidacionRequest pagarLiquidacion) throws ExceptionBean {
        securityTokenService.validateToken(auth.usuario().getJti());
        LOG.info("usuario = " + auth.usuario().getUserName());

//        PagarLiquidacionResponse res = new PagarLiquidacionResponse();
//        res.setSolicitudId(152018);
//        res.setDescripcion("Pago Solicitud Liquidado");
//        res.setTsCrea("2019-01-21T05:47:08.644");
//        res.setTotal(new BigDecimal("100.50"));
//        res.setEmail("PSILPAR@GMAIL.COM");
//        res.setSolicitante("SILVA PAREDES PERCY");
//        res.setTpoPago("TARJETA");
//        res.setPagoSolicitudId(2222222);
//        res.setNumeroRecibo("XXXXXXX");
//        res.setNumeroPublicidad("2021-691");
//        res.setCodVerificacion("3123123");
//        res.setSecReciDetaAtenNac(1111111);
//
//        return new ResponseEntity<>(res, HttpStatus.OK);
        LOG.info("solicitudId = " + String.valueOf(pagarLiquidacion.getSolicitudId()));
        LOG.info("detalle = " + String.valueOf(pagarLiquidacion.getDetalle()));
        LOG.info("monto = " + String.valueOf(pagarLiquidacion.getMonto()));
        LOG.info("usuario = " + String.valueOf(pagarLiquidacion.getUsuario()));
        String usuaModi = pagarLiquidacion.getUsuario().substring(0, Math.min(15, pagarLiquidacion.getUsuario().length()));
        pagarLiquidacion.setUsuario(usuaModi.toUpperCase());
        return new ResponseEntity<>(solicitudService.pagarLiquidacion(pagarLiquidacion, auth.usuario().getJti()), HttpStatus.OK);
    }

    @GetMapping(value = "/boleta/informativa/descarga/{transId}")
    public ResponseEntity<BoletaInformativaResponse> descargaBoletaInformativa(@PathVariable int transId, HttpServletRequest request, HttpServletResponse response) throws ExceptionBean {
        securityTokenService.validateToken(auth.usuario().getJti());
        LOG.info("usuario = " + auth.usuario().getUserName());
        BoletaInformativaResponse res = solicitudService.descargaBoletaInformativa(transId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    

    @GetMapping(value = "publicidad/{anho}/{numero}")
    public ResponseEntity<GetSolicitudResponse> getSolicitudPublicidad(@PathVariable String anho, 
			@PathVariable String numero) throws ExceptionBean {
    	securityTokenService.validateToken(auth.usuario().getJti());
		LOG.info("usuario = " + auth.usuario().getUserName());
		
        return new ResponseEntity<>(solicitudService.getSolicitudxNroPublicidad(anho, Long.valueOf(numero), auth.usuario().getJti()), HttpStatus.OK);
    }
    
}
