package pe.gob.sunarp.app.solicitud.service.impl;

import static pe.gob.sunarp.app.solicitud.util.Constantes.ATENCION_PENDIENTE;
import static pe.gob.sunarp.app.solicitud.util.Constantes.BLANK;
import static pe.gob.sunarp.app.solicitud.util.Constantes.BOLETA_INFORMATIVA;
import static pe.gob.sunarp.app.solicitud.util.Constantes.BUSQUEDA_NOMBRE;
import static pe.gob.sunarp.app.solicitud.util.Constantes.CERO_CERO;
import static pe.gob.sunarp.app.solicitud.util.Constantes.DESPACHADA;
import static pe.gob.sunarp.app.solicitud.util.Constantes.EMISOR;
import static pe.gob.sunarp.app.solicitud.util.Constantes.FLG_0;
import static pe.gob.sunarp.app.solicitud.util.Constantes.FLG_1;
import static pe.gob.sunarp.app.solicitud.util.Constantes.FLG_2;
import static pe.gob.sunarp.app.solicitud.util.Constantes.FLG_6;
import static pe.gob.sunarp.app.solicitud.util.Constantes.FLG_8;
import static pe.gob.sunarp.app.solicitud.util.Constantes.GENERIC_SUCCESS;
import static pe.gob.sunarp.app.solicitud.util.Constantes.POR_PAGAR;
import static pe.gob.sunarp.app.solicitud.util.Constantes.POR_VERIFICAR;
import static pe.gob.sunarp.app.solicitud.util.Constantes.PUBLICIDAD_COMPENDIOSA;
import static pe.gob.sunarp.app.solicitud.util.Constantes.VERIFICADOR;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import pe.gob.sunarp.app.seguridad.bean.UsuarioJtiBean;
import pe.gob.sunarp.app.solicitud.bean.AbonoBean;
import pe.gob.sunarp.app.solicitud.bean.ComprobanteBean;
import pe.gob.sunarp.app.solicitud.bean.DatosBoletaRPV;
import pe.gob.sunarp.app.solicitud.bean.ExtTbSubsanacionBean;
import pe.gob.sunarp.app.solicitud.bean.GenerarPublicidadBean;
import pe.gob.sunarp.app.solicitud.bean.GenericResponseBean;
import pe.gob.sunarp.app.solicitud.bean.MovimientoBean;
import pe.gob.sunarp.app.solicitud.bean.ObjetoSolicitudBean;
import pe.gob.sunarp.app.solicitud.bean.PagoSolicitudBean;
import pe.gob.sunarp.app.solicitud.bean.ReciDetaAtenNacBean;
import pe.gob.sunarp.app.solicitud.bean.RegularizaPagoBean;
import pe.gob.sunarp.app.solicitud.bean.ReingresoRequestBean;
import pe.gob.sunarp.app.solicitud.bean.SgmtSolicitudBean;
import pe.gob.sunarp.app.solicitud.bean.SolicitudBean;
import pe.gob.sunarp.app.solicitud.bean.SolicitudDataBean;
import pe.gob.sunarp.app.solicitud.bean.TransaccionBean;
import pe.gob.sunarp.app.solicitud.cache.RedisService;
import pe.gob.sunarp.app.solicitud.config.ApplicationProperties;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;
import pe.gob.sunarp.app.solicitud.exception.ExceptionProperties;
import pe.gob.sunarp.app.solicitud.mapper.ExtTbSubsanacionMapper;
import pe.gob.sunarp.app.solicitud.mapper.SgmtSolicitudMapper;
import pe.gob.sunarp.app.solicitud.mapper.SolicitudMapper;
import pe.gob.sunarp.app.solicitud.mapper.TransactionMapper;
import pe.gob.sunarp.app.solicitud.repository.SolicitudRepository;
import pe.gob.sunarp.app.solicitud.request.AclararSolicitudRequest;
import pe.gob.sunarp.app.solicitud.request.DescargaCertificadoRequest;
import pe.gob.sunarp.app.solicitud.request.DesistirSolicitudRequest;
import pe.gob.sunarp.app.solicitud.request.PagarLiquidacionRequest;
import pe.gob.sunarp.app.solicitud.request.SubsanarSolicitudRequest;
import pe.gob.sunarp.app.solicitud.response.BoletaInformativaResponse;
import pe.gob.sunarp.app.solicitud.response.DescargarCertificadoResponse;
import pe.gob.sunarp.app.solicitud.response.GetSolicitudResponse;
import pe.gob.sunarp.app.solicitud.response.GetTransaccionNewsResponse;
import pe.gob.sunarp.app.solicitud.response.GetTransaccionResponse;
import pe.gob.sunarp.app.solicitud.response.GuardarCajaResponse;
import pe.gob.sunarp.app.solicitud.response.PagarLiquidacionResponse;
import pe.gob.sunarp.app.solicitud.response.ProcesoResponse;
import pe.gob.sunarp.app.solicitud.response.SolicitudDocumentoResponse;
import pe.gob.sunarp.app.solicitud.restclient.CopiaLiteralVirtualRestClient;
import pe.gob.sunarp.app.solicitud.restclient.DescargaCertificadoRestClient;
import pe.gob.sunarp.app.solicitud.restclient.GenerarPublicidadRestClient;
import pe.gob.sunarp.app.solicitud.service.SolicitudService;
import pe.gob.sunarp.app.solicitud.util.Email;

@Slf4j
@Service
public class SolicitudServiceImpl implements SolicitudService {

    private static final Logger LOG = LoggerFactory.getLogger(SolicitudServiceImpl.class);

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private CopiaLiteralVirtualRestClient copiaLiteralVirtualRestClient;

    @Autowired
    private DescargaCertificadoRestClient descargaCertificadoRestClient;

    @Autowired
    private GenerarPublicidadRestClient generarPublicidadRestClient;

    @Autowired
    private ExceptionProperties exceptionProperties;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private SgmtSolicitudMapper sgmtSolicitudMapper;

    @Autowired
    private ExtTbSubsanacionMapper extTbSubsanacionMapper;

    @Autowired
    private SolicitudMapper solicitudMapper;


    @Autowired
    private RedisService redisService;

    @Override
    public List<GetTransaccionResponse> getTransactionFromUser(String jti) throws ExceptionBean {
        UsuarioJtiBean usuario = redisService.getUsuarioJti(jti);
        List<TransaccionBean> list = solicitudRepository.getTransactionFromUser(usuario.getUserKeyId());
        LOG.info("key = " + usuario.getUserKeyId());
        List<GetTransaccionResponse>  listResponse = new ArrayList<>();
        for(TransaccionBean item : list) {
        	GetTransaccionResponse respuesta = new GetTransaccionResponse();

        	respuesta.setAaPubl(item.getAaPubl());
        	respuesta.setTransId(item.getTransId());
        	respuesta.setFecHor(item.getFecHor());
        	respuesta.setServicioId(item.getServicioId());
        	respuesta.setCuentaId(item.getCuentaId());
        	respuesta.setStrBusq(item.getStrBusq());
        	respuesta.setCosto(item.getCosto());
        	respuesta.setSolicitudId(item.getSolicitudId());
        	respuesta.setNuPubl(item.getNuPubl());

        	if (item.getServicioId() == 219) {
        		respuesta.setTipo("1");
        		respuesta.setKey(item.getTransId());
            } else if ((item.getServicioId() >= 20 && item.getServicioId() <= 33 )
                    || item.getServicioId() == 220){
                /*Busqueda Nombre = 2*/
            	respuesta.setTipo("2");
            } else {
                /*Publicidad Compendiosa = 3*/
            	respuesta.setTipo("3");

                if(item.getSolicitudId() > 0) {
                	respuesta.setKey(item.getSolicitudId());  
                }
            }
        	
        	listResponse.add(respuesta);
        }
        
        return listResponse;

    }
    
    @Override
    public List<GetTransaccionNewsResponse> getTransactionFromUserNews(String jti) throws ExceptionBean {
        UsuarioJtiBean usuario = redisService.getUsuarioJti(jti);
        List<TransaccionBean> list = solicitudRepository.getTransactionFromUserNews(usuario.getUserKeyId());
        
        List<GetTransaccionNewsResponse> resultado = new ArrayList<GetTransaccionNewsResponse>();
        
        for (TransaccionBean transacBean: list) {
        	GetTransaccionNewsResponse resultBean = new GetTransaccionNewsResponse();
        	resultBean.setFecHor(transacBean.getFecHor());
        	resultBean.setStrBusq(transacBean.getStrBusq());       	

        	if(transacBean.getSolicitudId() > 0) {
        		if(transacBean.getEstado() == null) {
        			resultBean.setEstado("-");
        		}
        		else {
        			resultBean.setEstado(transacBean.getEstado());
        		}

        		 resultBean.setSolicitudId(transacBean.getSolicitudId());
            }
            else {
            	resultBean.setEstado("-");
            }
            resultado.add(resultBean);
        	
        }
        
        return resultado;

    }

    private GetSolicitudResponse getSolicitudNews(String id) throws ExceptionBean {
        SolicitudDataBean solicitudData = solicitudRepository.getDataSolicitudNews(id);
        if(solicitudData!= null) {
        	return solicitudMapper.toGetSolicitudResponse(solicitudData);
        }
        else {
        	return null;
        }
    }
    
    @Override
    public GetSolicitudResponse getSolicitud(String id) throws ExceptionBean {
        SolicitudDataBean solicitudData = solicitudRepository.getDataSolicitud(id);
        
        String estadoDesc = this.setEstado(solicitudData.getEstado(), solicitudData.getFlgObs(), 
        		solicitudData.getFlgAclar(), solicitudData.getFlgDesis(), 
        		solicitudData.getFlgDeneg(), solicitudData.getFlgAban()) ;

        solicitudData.setEstado(estadoDesc);
		
        return solicitudMapper.toGetSolicitudResponse(solicitudData);
    }

    private String setEstado(String estado, String flgObs, String flgAclar, String flgDesis, String flgDeneg, String flgAban) throws ExceptionBean {
    	

        String estadoDesc = estado;
        LOG.info("getFlgObs = " + flgObs);
		if(flgObs != null) {
			switch (flgObs){
				case "1": estadoDesc += ", Observado";
					break;
				case "2": estadoDesc += ", Observación Reingresada";
					break;
			}
		}
        LOG.info("getFlgAclar = " + flgAclar);
		if(flgAclar != null) {
			switch (flgAclar){
				case "1": estadoDesc += ", Aclaratoria Solicitada";
					break;
				case "2": estadoDesc += ", Aclaratoria Aceptada";
					break;
				case "3": estadoDesc += ", Aclaratoria Denegada";
					break;
			}
		}
        LOG.info("getFlgDesis = " + flgDesis);
		if(flgDesis != null) {
			switch (flgDesis){
				case "1": estadoDesc += ", Desistimiento Solicitado";
					break;
				case "2": estadoDesc += ", Desistimiento Aceptado";
					break;
				case "3": estadoDesc += ", Desistimiento Denegado";
					break;
			}
		}
        LOG.info("getFlgDeneg = " + flgDeneg);
		if(flgDeneg != null && flgDeneg.equals("1")) {
			estadoDesc += ", Solicitud Denegada";
		}
        LOG.info("getFlgAban = " + flgAban);
		if(flgAban != null && flgAban.equals("1")) {
			estadoDesc += ", Solicitud con Abandono";
		}
        LOG.info("setEstado = " + estadoDesc);
		return estadoDesc;
    }

	@Override
	public GetSolicitudResponse getSolicitudxNroPublicidad(String anho, long numero, String jti) throws ExceptionBean {
		UsuarioJtiBean usuarioBean = redisService.getUsuarioJti(jti);
		if(usuarioBean == null) {
			throw exceptionProperties.getUnauthorized();			
		}
		LOG.info("getSolicitudxNroPublicidad");
    	SolicitudDataBean solicitudData = solicitudRepository.buscarSolicitudxNroPublicidad(anho, numero);
    	
        String estadoDesc = this.setEstado(solicitudData.getEstado(), solicitudData.getFlgObs(), 
        		solicitudData.getFlgAclar(), solicitudData.getFlgDesis(), 
        		solicitudData.getFlgDeneg(), solicitudData.getFlgAban()) ;

        solicitudData.setEstado(estadoDesc);

    	return solicitudMapper.toGetSolicitudResponse(solicitudData);
    }
	
	
    @Override
    public SolicitudDocumentoResponse getSolicitudDocumento(String id) throws ExceptionBean {
        SolicitudDocumentoResponse response = new SolicitudDocumentoResponse();
        SolicitudDataBean solicitudDataBean = solicitudRepository.getDataSolicitud(id);
        if(Optional.ofNullable(solicitudDataBean.getAaPubl()).isPresent()
                && Optional.ofNullable(solicitudDataBean.getNuPubl()).isPresent()) {

            List<String> listaCodRgst = solicitudRepository.getCodRgst(solicitudDataBean.getAaPubl(),
                    Long.parseLong(solicitudDataBean.getNuPubl()));
            String codRgst = listaCodRgst.get(0);
            String codTpoEsquela = solicitudDataBean.getCodTpoEsquela();

            if(Optional.ofNullable(codTpoEsquela).isPresent()){
                DescargaCertificadoRequest reqDownloadCert = DescargaCertificadoRequest.builder()
                        .codigoZona(solicitudDataBean.getCodZona())
                        .codigoOficina(solicitudDataBean.getCodOficina())
                        .anioPubl(solicitudDataBean.getAaPubl())
                        .numPubl(solicitudDataBean.getNuPubl())
                        .codigoServicio(codRgst)
                        .codigoTipoEsquela(codTpoEsquela)
                        .build();
                DescargarCertificadoResponse certificado = descargaCertificadoRestClient.descargarCertificado(reqDownloadCert);
                if (!Optional.ofNullable(certificado).isPresent()){
                    throw exceptionProperties.getNotFound();
                }
                response.setDocumento(certificado.getDocumento());
                response.setFileName(certificado.getFileName());
            }
        } else {
            throw exceptionProperties.getNotFound();
        }
        return response;
    }

    @Override
    public ProcesoResponse aclararSolicitud(AclararSolicitudRequest aclararSolicitud, String jti) throws ExceptionBean {
        UsuarioJtiBean usuario = redisService.getUsuarioJti(jti);
        SolicitudBean solicitud = solicitudRepository.getSolicitudById(aclararSolicitud.getSolicitudId());
        if (solicitud.getEstado().equals(POR_PAGAR)
                || !solicitud.getUserKeyId().equals(usuario.getUserKeyId())
                || Optional.ofNullable(solicitud.getCsolflgaban()).orElse(FLG_0).equals(FLG_1)
                || Optional.ofNullable(solicitud.getCsolFlgdeneg()).orElse(FLG_0).equals(FLG_1)) {
            throw exceptionProperties.getNotFound();
        }
        ProcesoResponse response = new ProcesoResponse();
        if (solicitud.getEstado().equals(DESPACHADA)){
            ObjetoSolicitudBean objSolicitud = solicitudRepository.getObjetoSolicitudBySolicitudId(aclararSolicitud.getSolicitudId());
            if(Optional.ofNullable(objSolicitud.getAaPubl()).isPresent()
                    && Optional.ofNullable(objSolicitud.getNuPubl()).isPresent()) {
                ReingresoRequestBean reIngreso = ReingresoRequestBean.builder()
                        .anioPublicidad(objSolicitud.getAaPubl())
                        .desTipReing(aclararSolicitud.getDetalle())
                        .igReing(null)
                        .numPublicidad(Long.parseLong(objSolicitud.getNuPubl()))
                        .coOficRegi(objSolicitud.getOficRegId())
                        .coZonaRegi(objSolicitud.getRegPubId())
                        .codTipReing(FLG_6)
                        .build();
                GenericResponseBean resp = copiaLiteralVirtualRestClient.generarReingresoPublicidad(reIngreso);

                if(!resp.getCode().equals(GENERIC_SUCCESS)){
                    ExceptionBean ex = exceptionProperties.getReEntryFailed();
                    ex.setDescription(ex.getDescription().concat(resp.getCode()));
                    throw ex;
                }
            }

            SgmtSolicitudBean sgmt = sgmtSolicitudMapper.toSgmtSolicitudBeanFromAclaratoria(aclararSolicitud, solicitud);
            solicitudRepository.saveSgmtSolicitud(sgmt);

            solicitud.setEstado(POR_VERIFICAR);
            solicitud.setCsolFlgliq(FLG_0);
            solicitud.setCsolEstado(FLG_0);
            solicitud.setCsolFlgobs(FLG_0);
            solicitud.setCsolFlgrec(FLG_0);
            solicitud.setCsolFlgAclar(FLG_1);
            solicitud.setTsModi(LocalDateTime.now());
            solicitud.setUsrModi(aclararSolicitud.getUsuario());
            solicitudRepository.updateSolicitud(solicitud);

            solicitudRepository.updateSolicitudXCarga(Long.parseLong(solicitud.getSolicitudId()), ATENCION_PENDIENTE, BLANK);

            solicitudRepository.updateDocEmitidoAclaratoria(Long.parseLong(solicitud.getSolicitudId()), FLG_2, FLG_1);

            response.setCodResult("1");
            response.setMsgResult("Exito");
        }
        else {
            response.setCodResult("0");
            response.setMsgResult("No se puede Aclarar Solicitud");
        }
        return response;
    }

    @Override
    public ProcesoResponse subsanarSolicitud(SubsanarSolicitudRequest subsanarSolicitud, String jti) throws ExceptionBean {
        UsuarioJtiBean usuario = redisService.getUsuarioJti(jti);
        SolicitudBean solicitud = solicitudRepository.getSolicitudById(subsanarSolicitud.getSolicitudId());
        if (solicitud.getEstado().equals(POR_PAGAR)) {
            throw exceptionProperties.getNotFound();
        }
        ProcesoResponse response = new ProcesoResponse();
        if (solicitud.getUserKeyId().equals(usuario.getUserKeyId()) &&
                Optional.ofNullable(solicitud.getCsolFlgobs()).orElse(FLG_0).equals(FLG_1)){
            ObjetoSolicitudBean objSolicitud = solicitudRepository.getObjetoSolicitudBySolicitudId(subsanarSolicitud.getSolicitudId());
            if(Optional.ofNullable(objSolicitud.getAaPubl()).isPresent()
                    && Optional.ofNullable(objSolicitud.getNuPubl()).isPresent()) {
                ReingresoRequestBean reIngreso = ReingresoRequestBean.builder()
                        .anioPublicidad(objSolicitud.getAaPubl())
                        .desTipReing(subsanarSolicitud.getDetalle())
                        .igReing(null)
                        .numPublicidad(Long.parseLong(objSolicitud.getNuPubl()))
                        .coOficRegi(objSolicitud.getOficRegId())
                        .coZonaRegi(objSolicitud.getRegPubId())
                        .codTipReing(FLG_1)
                        .build();
                GenericResponseBean resp = copiaLiteralVirtualRestClient.generarReingresoPublicidad(reIngreso);

                if(!resp.getCode().equals(GENERIC_SUCCESS)){
                    ExceptionBean ex = exceptionProperties.getReEntryFailed();
                    ex.setDescription(ex.getDescription().concat(resp.getCode()));
                    throw ex;
                }
            }

            SgmtSolicitudBean sgmt = sgmtSolicitudMapper.toSgmtSolicitudBeanFromSubsanar(subsanarSolicitud, solicitud);
            solicitudRepository.saveSgmtSolicitud(sgmt);

            solicitud.setEstado(POR_VERIFICAR);
            solicitud.setCsolFlgobs(FLG_2);
            solicitudRepository.updateSolicitud(solicitud);

            ExtTbSubsanacionBean extTbSubsanacion = extTbSubsanacionMapper.toExtTbSubsanacionFromSubsanar(subsanarSolicitud, solicitud);
            solicitudRepository.saveExtTbSubsanacion(extTbSubsanacion);

            String[] roles = new String[2];
            roles[0] = EMISOR;
            roles[1] = VERIFICADOR;

            for (String rol : roles) {
                solicitudRepository.updateSolicitudXCarga(Long.parseLong(solicitud.getSolicitudId()), ATENCION_PENDIENTE, rol);
            }
            response.setCodResult("1");
            response.setMsgResult("Exito");
        }
        else {
	        response.setCodResult("0");
	        response.setMsgResult("No se puede Subsanar Solicitud");
        }
        return response;
    }

    @Override
    public ProcesoResponse desistirSolicitud(DesistirSolicitudRequest desistirSolicitud, String jti) throws ExceptionBean {
        UsuarioJtiBean usuario = redisService.getUsuarioJti(jti);
        LOG.info("getSolicitudId = " + desistirSolicitud.getSolicitudId());
        SolicitudBean solicitud = solicitudRepository.getSolicitudById(desistirSolicitud.getSolicitudId());
        LOG.info("getEstado = " + solicitud.getEstado());
        if (solicitud.getEstado().equals(POR_PAGAR)) {
            throw exceptionProperties.getNotFound();
        }
        ProcesoResponse response = new ProcesoResponse();
        LOG.info(" solicitud getUserKeyId = " + solicitud.getUserKeyId());
        LOG.info("usuario getUserKeyId = " + usuario.getUserKeyId());
        if (solicitud.getUserKeyId().equals(usuario.getUserKeyId())
                && solicitud.getEstado().equals(POR_VERIFICAR)){
            LOG.info("getObjetoSolicitudBySolicitudId = " + desistirSolicitud.getSolicitudId());
            ObjetoSolicitudBean objSolicitud = solicitudRepository.getObjetoSolicitudBySolicitudId(desistirSolicitud.getSolicitudId());
            LOG.info("getCertificadoId = " + objSolicitud.getCertificadoId());
            //if(applicationProperties.getCertiCopiaLiteral().contains(objSolicitud.getCertificadoId())) {
                if(Optional.ofNullable(objSolicitud.getAaPubl()).isPresent()
                        && Optional.ofNullable(objSolicitud.getNuPubl()).isPresent()) {
                    ReingresoRequestBean reIngreso = ReingresoRequestBean.builder()
                            .anioPublicidad(objSolicitud.getAaPubl())
                            .desTipReing(BLANK)
                            .igReing(null)
                            .numPublicidad(Long.parseLong(objSolicitud.getNuPubl()))
                            .coOficRegi(objSolicitud.getOficRegId())
                            .coZonaRegi(objSolicitud.getRegPubId())
                            .codTipReing(FLG_8)
                            .build();
                    GenericResponseBean resp = copiaLiteralVirtualRestClient.generarReingresoPublicidad(reIngreso);

                    if(!resp.getCode().equals(GENERIC_SUCCESS)){
                        ExceptionBean ex = exceptionProperties.getReEntryFailed();
                        ex.setDescription(ex.getDescription().concat(resp.getCode()));
                        throw ex;
                    }
                    solicitudRepository.solicitarDesistimiento(Long.parseLong(desistirSolicitud.getSolicitudId()),
                                desistirSolicitud.getUsuario());
                    response.setCodResult("1");
                    response.setMsgResult("Exito");
                    return response;

                }
                /*
            } else {
                LOG.info("solicitarDesistimiento = " + desistirSolicitud.getSolicitudId());

                solicitudRepository.solicitarDesistimiento(Long.parseLong(desistirSolicitud.getSolicitudId()),
                        desistirSolicitud.getUsuario());
                response.setCodResult("1");
                response.setMsgResult("Exito");
                return response;
            }*/
        }
        response.setCodResult("0");
        response.setMsgResult("No se puede Desistir Solicitud");
        return response;
    }

    @Override
    public PagarLiquidacionResponse pagarLiquidacion(PagarLiquidacionRequest pagarLiquidacion, String jti) throws ExceptionBean {
        UsuarioJtiBean usuario = redisService.getUsuarioJti(jti);
        PagarLiquidacionResponse reciboPubl = this.realizarPago(pagarLiquidacion);

        RegularizaPagoBean regularizaPagoBean = new RegularizaPagoBean();
        regularizaPagoBean.setUsrKeyId(usuario.getUserKeyId());
        regularizaPagoBean.setTransId((int)reciboPubl.getSolicitudId());
        regularizaPagoBean.setVisanetResponse(pagarLiquidacion.getVisanetResponse());
        regularizaPagoBean.setCostoTotal(new BigDecimal(pagarLiquidacion.getMonto()));

        PagarLiquidacionResponse resp = new PagarLiquidacionResponse();
        resp.setSolicitudId(reciboPubl.getSolicitudId());
        resp.setDescripcion(reciboPubl.getDescripcion());
        resp.setTsCrea(reciboPubl.getTsCrea());
        resp.setTotal(reciboPubl.getTotal());
        resp.setEmail(usuario.getEmail());
        String nombre = (usuario.getPriApe() != null  ? usuario.getPriApe() + " " : "") +
                (usuario.getSegApe() != null ? usuario.getSegApe() + ", " : "") +
                (usuario.getNombres() != null ? usuario.getNombres() : "");
        resp.setSolicitante(nombre);
        resp.setTpoPago("Tarjeta");

        ObjetoSolicitudBean objSolicitud = solicitudRepository.getObjetoSolicitudBySolicitudId(String.valueOf(reciboPubl.getSolicitudId()));

        if(reciboPubl.getSecReciDetaAtenNac() > 0){
            GenerarPublicidadBean generarPublicidadBean = GenerarPublicidadBean.builder()
                    .tipDocumento(usuario.getTipoDoc())
                    .numDocumento(usuario.getNroDoc())
                    .secuIdRecibo(String.valueOf(reciboPubl.getSecReciDetaAtenNac()))
                    .solicitudId(String.valueOf(reciboPubl.getSolicitudId()))
                    .codVerificacion(objSolicitud.getCcodDescarga())
                    .coZonaRegi("01")
                    .cantRecibos("1")
                    .codMoneda("")
                    .coOficRegi("01")
                    .codCaja("1")
                    .coLocaAten("00")
                    .build();
            GuardarCajaResponse cajaResponse = generarPublicidadRestClient.generaPublicidad(generarPublicidadBean);
            if(cajaResponse.getCode().equals(GENERIC_SUCCESS)){
                String codVerificacion = this.getCodVerificacion(8);
                String publicidad = objSolicitud.getAaPubl().concat("-").concat(String.valueOf(objSolicitud.getNuPubl()));
                regularizaPagoBean.setNumeroPublicidad(publicidad);
                regularizaPagoBean.setNumeroRecibo(cajaResponse.getRespuestaTxcaja().getNumeroRecibo());
                regularizaPagoBean.setCodVerificacion(codVerificacion);

                resp.setNumeroPublicidad(publicidad);
                resp.setNumeroRecibo(cajaResponse.getRespuestaTxcaja().getNumeroRecibo());
                resp.setCodVerificacion(codVerificacion);
            }
        }
        solicitudRepository.savePago(regularizaPagoBean);
        enviarCorreo(regularizaPagoBean, resp);

        return resp;
    }

    @Override
    public BoletaInformativaResponse descargaBoletaInformativa(int transId) throws ExceptionBean {
        TransaccionBean transaccion = solicitudRepository.getTransactionById(String.valueOf(transId));
        String str = "";
        DatosBoletaRPV datosBoletaRPV;
        JasperPrint jasperPrint = null;
        BoletaInformativaResponse response = new BoletaInformativaResponse();
        byte[] archivo = null;
        try {
            if (Optional.ofNullable(transaccion.getBlobJson()).isPresent()){
                str = new String(transaccion.getBlobJson(), "UTF-8");
                ObjectMapper mapper = new ObjectMapper();
                datosBoletaRPV = mapper.readValue(str, DatosBoletaRPV.class);
                log.info("datosBoletaRPV: " + new Gson().toJson(datosBoletaRPV, DatosBoletaRPV.class));
                archivo = this.generaBoletaInformativa(datosBoletaRPV, transId, transaccion);
            } else {
                log.error("not found");
                throw exceptionProperties.getNotFound();
            }
        } catch(Exception e) {
            e.printStackTrace();
            log.error("Error al convertir blob json");
            throw exceptionProperties.getNotFound();
        }
        response.setBoleta(archivo);
        return response;
    }

    private byte[] generaBoletaInformativa(DatosBoletaRPV datosRPV, int transId, TransaccionBean transaccion) throws Exception {

        JasperReport jasperReport = null;
        Map<String, Object> pars = new HashMap<String, Object>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(transaccion.getFecHor(), formatter);

        pars.put("detVeh", datosRPV.getDatosBoletaRPV());
        pars.put("usuario", datosRPV.getUsuario());
        pars.put("numId", String.valueOf(transId));
        pars.put("montoP", String.valueOf(datosRPV.getCosto()));
        pars.put("fecha", sdf.format(Date.from(dateTime.atZone(ZoneId.of("America/Lima")).toInstant())));
        /*
        pars.put("urlPropietarios", "/report/Propietarios.jrxml");
        pars.put("urlCaracteristicas", "./report/Caracteristicas.jrxml");
        pars.put("urlAfectaciones", "./report/Afectaciones.jrxml");
        pars.put("urlTitulosPendientes", "./report/TitulosPendientes.jrxml");
        pars.put("urlParticipantes", "./report/Participantes.jrxml"); */
        pars.put("urlImage", SolicitudServiceImpl.class.getResourceAsStream("/report/SUNARP.jpg"));
        byte[] archivo = null;
        try{
            log.info("Compiling report... ");
            jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/boletaInformativa.jrxml"));
            log.info("Reporte compilado. ");
            JasperReport jrptPropietarios = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/Propietarios.jrxml"));
            JasperReport jrptCaracteristicas = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/Caracteristicas.jrxml"));
            JasperReport jrptAfectaciones = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/Afectaciones.jrxml"));
            JasperReport jrptTitulosPendientes = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/TitulosPendientes.jrxml"));
            JasperReport jrptParticipantes = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/Participantes.jrxml"));
            pars.put("rptPropietarios", jrptPropietarios);
            pars.put("rptCaracteristicas", jrptCaracteristicas);
            pars.put("rptAfectaciones", jrptAfectaciones);
            pars.put("rptTitulosPendientes", jrptTitulosPendientes);
            pars.put("rptParticipantes", jrptParticipantes);
            archivo = JasperRunManager.runReportToPdf(jasperReport, pars);
        }catch(Exception e){
            log.error("generaBoletaInformativa: " + e.getMessage());
        }
        if(!Optional.ofNullable(archivo).isPresent()){
            throw exceptionProperties.getCompileFailed();
        }
        return archivo;
    }

    private void enviarCorreo(RegularizaPagoBean regularizaPagoBean, PagarLiquidacionResponse resp) throws ExceptionBean {
        String mensaje = "";
        try {
            mensaje = buildMessage(regularizaPagoBean, resp,"P");
            String appName = "App Sunarp";
            Email email = new Email();
            int rpt = email.sendEmail(
                    new String[]{
                            resp.getEmail()
                    }
                    ,appName + " - Pago de Servicio"
                    ,mensaje, null, false, false, null, new String[]{"app@sunarp.gob.pe"}, null, appName);

        } catch(Exception e){
            log.error("Ocurrio un error al enviar el mensaje: {}", e.getLocalizedMessage());
        }
    }

    private String buildMessage(RegularizaPagoBean datos, PagarLiquidacionResponse resp, String tipoServicio) throws Exception{
        //-- BODY
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/pages/recibo.html"), "UTF-8")
        );
        for (int c = br.read(); c != -1; c = br.read()) sb.append((char)c);
        String body = sb.toString().trim();

        if(tipoServicio.equals("P")){
            StringBuffer texto = new StringBuffer("<tr class='ui-widget-content' role='row'><td width='0' height='0' class='style-column1' role='gridcell'><label class='ui-outputlabel' style='font-weight:bold;'>SOLICITUD No.</label>");
            texto.append("</td><td width='0' height='0' class='style-column2' role='gridcell'><label class='ui-outputlabel'>").append(String.valueOf(resp.getSolicitudId())).append("</label></td></tr>");
            if( datos.getCodCerti() != null
                    && (applicationProperties.getCertiSprn().contains(datos.getCodCerti())
                    || applicationProperties.getCertiCopiaLiteral().contains(datos.getCodCerti()) ) )
            {
                texto.append("<tr class='ui-widget-content' role='row'><td width='0' height='0' class='style-column1' role='gridcell'><label class='ui-outputlabel' style='font-weight:bold;'>PUBLICIDAD No.</label>");
                texto.append("</td><td width='0' height='0' class='style-column2' role='gridcell'><label class='ui-outputlabel'>").append(String.valueOf(datos.getNumeroPublicidad())).append("</label></td></tr>");

                texto.append("<tr class='ui-widget-content' role='row'><td width='0' height='0' class='style-column1' role='gridcell'><label class='ui-outputlabel' style='font-weight:bold;'>COMPROBANTE No.</label>");
                texto.append("</td><td width='0' height='0' class='style-column2' role='gridcell'><label class='ui-outputlabel'>").append(String.valueOf(datos.getNumeroRecibo())).append("</label></td></tr>");

                texto.append("<tr class='ui-widget-content' role='row'><td width='0' height='0' class='style-column1' role='gridcell'><label class='ui-outputlabel' style='font-weight:bold;'>COD. VERIFICACI&Oacute;N</label>");
                texto.append("</td><td width='0' height='0' class='style-column2' role='gridcell'><label class='ui-outputlabel'>").append(String.valueOf(datos.getCodVerificacion())).append("</label></td></tr>");
            }

            StringBuffer aviso =  new StringBuffer("<tr class='ui-widget-content' role='row'><td role='gridcell'><br/><label class='ui-outputlabel'>IMPORTANTE:<br/>"
                    + "La atenci&oacute;n de los certificados se realiza en un plazo m&aacute;ximo de tres (03) d&iacute;as <br/>h&aacute;biles, "
                    + "los mismos que ser&aacute;n enviados al correo electr&oacute;nico consignado <br/>al momento de su suscripci&oacute;n.<br/>"
                    + "Adicionalmente, podr&aacute;n ser visualizados en la opci&oacute;n 'Ver historial' ubicado<br>dentro del &iacute;cono 'Mi Perfil' del App Sunarp. </label></td></tr>");

            body = body.replaceAll("###_TIPOSERV_###", PUBLICIDAD_COMPENDIOSA);
            body = body.replaceAll("###_TEXTOCOMPENDIOSO_###", texto.toString());
            body = body.replaceAll("###_AVISO_###", aviso.toString());
        }else if (tipoServicio.equals("B")){
            StringBuffer aviso =  new StringBuffer("<tr class='ui-widget-content' role='row'><td role='gridcell'><br/><label class='ui-outputlabel'>IMPORTANTE:<br/>"
                    + "La boleta informativa no es enviada por correo electr&oacute;nico.<br/>Para ver o descargar este documento debe ingresar a 'Mi Perfil' y luego 'Ver historial'.<br/>"
                    + "Si usa Android, debe verificar los permisos de nuestra App en su dispositivo y adicionalmente se le sugiere tener instalada una aplicaci&oacute;n para abrir archivos PDF."
                    + "</label></td></tr>");
            body = body.replaceAll("###_TIPOSERV_###", BOLETA_INFORMATIVA);
            body = body.replaceAll("###_TEXTOCOMPENDIOSO_###", "");
            body = body.replaceAll("###_AVISO_###", aviso.toString());
        }else{
            StringBuffer aviso =  new StringBuffer("<tr class='ui-widget-content' role='row'><td role='gridcell'><br/><label class='ui-outputlabel'>IMPORTANTE:<br/>"
                    + "El resultado de la b&uacute;squeda le ser&aacute; enviado a <br/>su correo electr&oacute;nico."
                    + "</label></td></tr>");
            body = body.replaceAll("###_TIPOSERV_###", BUSQUEDA_NOMBRE);
            body = body.replaceAll("###_TEXTOCOMPENDIOSO_###", "");
            body = body.replaceAll("###_AVISO_###", aviso.toString());
        }

        body = body.replaceAll("###_TRANSACCION_###", String.valueOf(datos.getTransId()));
        body = body.replaceAll("###_SERVICIO_###", resp.getDescripcion());
        body = body.replaceAll("###_FECHA_###", resp.getTsCrea());
        body = body.replaceAll("###_MONTO_###", resp.getTotal().toString());
        body = body.replaceAll("###_MAIL_###", resp.getEmail());
        body = body.replaceAll("###_NOMBRE_###", resp.getSolicitante());

        return body;
    }

    private String getCodVerificacion(int n) {
        int m = (int) Math.pow(10, n - 1);
        return String.valueOf( m + new Random().nextInt(9 * m));
    }

    private PagarLiquidacionResponse realizarPago(PagarLiquidacionRequest pagarLiquidacion) throws ExceptionBean {
        PagarLiquidacionResponse recibo = new PagarLiquidacionResponse();
        long lineaPrepagoId = applicationProperties.getLineaPrepago();
        MovimientoBean movimientoBean = MovimientoBean.builder()
                .tpoMov(FLG_0)
                .fecHor(LocalDateTime.now())
                .fgAsig(FLG_0)
                .lineaPrepago(lineaPrepagoId)
                .montoFin(new BigDecimal(CERO_CERO))
                .build();

        long movimientoId = solicitudRepository.saveMovimiento(movimientoBean);

        long personaId = solicitudRepository.getPersonaIdByLineaPrepagoId(lineaPrepagoId);

        AbonoBean abono = AbonoBean.builder()
                .tipoAbono("V")
                .tipoVent("P")
                .tpoPagVent("T")
                .usrCaja(pagarLiquidacion.getUsuario())
                .tipoUsr("I")
                .monto(new BigDecimal(pagarLiquidacion.getMonto()))
                .movimientoId(movimientoId)
                .oficRegId("00")
                .regPubId("00")
                .fgCierre(FLG_0)
                .personaId(personaId)
                .tsCrea(LocalDateTime.now())
                .tsModi(LocalDateTime.now())
                .estado(FLG_1)
                .build();
        long abonoId = solicitudRepository.saveAbono(abono);

        ComprobanteBean comprobanteBean = ComprobanteBean.builder()
                .abonoId(abonoId)
                .monto(new BigDecimal(pagarLiquidacion.getMonto()))
                .estado(FLG_1)
                .build();
        solicitudRepository.saveComprobante(comprobanteBean);

        // Actualizamos el estado liquidado a 0 -  pagada
        SolicitudBean solicitudBean = solicitudRepository.getSolicitudById(String.valueOf(pagarLiquidacion.getSolicitudId()));
        solicitudBean.setCsolEstado(FLG_0);
        solicitudBean.setEstado(POR_VERIFICAR);
        solicitudRepository.updateSolicitud(solicitudBean);

        //Se guarda el seguimiento
        SgmtSolicitudBean sgmtSolicitudBean = sgmtSolicitudMapper.toSgmtSolicitudBeanFromPagarLiquidacion(pagarLiquidacion);
        solicitudRepository.saveSgmtSolicitud(sgmtSolicitudBean);

        PagoSolicitudBean pagoSolicitudBean = PagoSolicitudBean.builder()
                .solicitudId(pagarLiquidacion.getSolicitudId())
                .tpoPago("L")// Pago Linea Prepago
                .abonoId(abonoId)
                .monto(new BigDecimal(pagarLiquidacion.getMonto()))
                .tsCrea(LocalDateTime.now())
                .tsModi(LocalDateTime.now())
                .usrCrea(pagarLiquidacion.getUsuario())
                .usrModi(pagarLiquidacion.getUsuario())
                .cpasolFlg(FLG_1)// pago por liquidacion
                .build();
        long pagoSolicitudId = solicitudRepository.savePagoSolicitud(pagoSolicitudBean);

        ObjetoSolicitudBean objSolicitud = solicitudRepository
                .getObjetoSolicitudBySolicitudId(String.valueOf(pagarLiquidacion.getSolicitudId()));
        if(applicationProperties.getCertiSprn().contains(objSolicitud.getCertificadoId())) {
            ReciDetaAtenNacBean reciDetaAtenNac = solicitudRepository.getReciDetaAtenNac(objSolicitud.getAaPubl(),
                    Long.parseLong(objSolicitud.getNuPubl()));
            long codServLiq = solicitudRepository.getCodServicioLiquidacion(reciDetaAtenNac.getCoTipoRgst());
            ReciDetaAtenNacBean  nuevoReciDetaAtenNac = ReciDetaAtenNacBean.builder()
                    .aaReciTmp(reciDetaAtenNac.getAaReciTmp())
                    .anPubl(reciDetaAtenNac.getAnPubl())
                    .nuPubl(reciDetaAtenNac.getNuPubl())
                    .coZonaRegiDest(reciDetaAtenNac.getCoZonaRegiDest())
                    .coOficRegiDest(reciDetaAtenNac.getCoOficRegiDest())
                    .coZonaRegiPart(reciDetaAtenNac.getCoZonaRegiPart())
                    .coOficRegiPart(reciDetaAtenNac.getCoOficRegiPart())
                    .coServRgst(new BigDecimal(codServLiq))
                    .coTipoRgst(reciDetaAtenNac.getCoTipoRgst())
                    .coTipoServ("005")
                    .solicitudId(reciDetaAtenNac.getSolicitudId())
                    .tsCrea(LocalDateTime.now())
                    .inEstd(reciDetaAtenNac.getInEstd())
                    .build();
            long reciDetaId = solicitudRepository.saveReciDetaAtenNac(nuevoReciDetaAtenNac);
            recibo.setSecReciDetaAtenNac(reciDetaId);
        }

        recibo.setTpoPago("TARJETA");
        recibo.setSolicitudId(pagarLiquidacion.getSolicitudId());
        recibo.setDescripcion("Pago Solicitud Liquidado");
        recibo.setTsCrea(LocalDateTime.now().toString());
        recibo.setTotal(new BigDecimal(pagarLiquidacion.getMonto()));
        recibo.setPagoSolicitudId(pagoSolicitudId);

        return recibo;
    }
}
