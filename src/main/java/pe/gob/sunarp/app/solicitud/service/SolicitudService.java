package pe.gob.sunarp.app.solicitud.service;

import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;
import pe.gob.sunarp.app.solicitud.request.AclararSolicitudRequest;
import pe.gob.sunarp.app.solicitud.request.DesistirSolicitudRequest;
import pe.gob.sunarp.app.solicitud.request.PagarLiquidacionRequest;
import pe.gob.sunarp.app.solicitud.request.SubsanarSolicitudRequest;
import pe.gob.sunarp.app.solicitud.response.*;

import java.util.List;

public interface SolicitudService {

    List<GetTransaccionResponse> getTransactionFromUser(String jti) throws ExceptionBean;
    List<GetTransaccionNewsResponse> getTransactionFromUserNews(String jti) throws ExceptionBean;
    GetSolicitudResponse getSolicitud(String id) throws ExceptionBean;
    ProcesoResponse aclararSolicitud(AclararSolicitudRequest aclararSolicitud, String jti) throws ExceptionBean;
    ProcesoResponse subsanarSolicitud(SubsanarSolicitudRequest subsanarSolicitud, String jti) throws ExceptionBean;
    ProcesoResponse desistirSolicitud(DesistirSolicitudRequest desistirSolicitud, String jti) throws ExceptionBean;
    SolicitudDocumentoResponse getSolicitudDocumento(String id) throws ExceptionBean;
    PagarLiquidacionResponse pagarLiquidacion(PagarLiquidacionRequest pagarLiquidacion, String jti) throws ExceptionBean;
    BoletaInformativaResponse descargaBoletaInformativa(int transId) throws ExceptionBean ;
    
    GetSolicitudResponse getSolicitudxNroPublicidad(String anho, long numero, String jti) throws ExceptionBean;
}
