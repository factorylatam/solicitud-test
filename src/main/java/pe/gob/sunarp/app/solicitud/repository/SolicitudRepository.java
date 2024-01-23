package pe.gob.sunarp.app.solicitud.repository;

import pe.gob.sunarp.app.solicitud.bean.*;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;

import java.util.List;

public interface SolicitudRepository {
    List<TransaccionBean> getTransactionFromUser(String key) throws ExceptionBean;
    String getSolicitudId(String transId) throws ExceptionBean;
    ObjetoSolicitudBean getObjetoSolicitudBySolicitudId(String solicitudId) throws ExceptionBean;
    SolicitudBean getSolicitudById(String solicitudId) throws ExceptionBean;
    SolicitudDataBean getDataSolicitud(String solicitudId) throws ExceptionBean;
    void saveSgmtSolicitud(SgmtSolicitudBean sgmtSolicitudBean) throws ExceptionBean;
    void updateSolicitud(SolicitudBean solicitud) throws ExceptionBean;
    void updateSolicitudXCarga(long solicitudId, String estado, String rol) throws ExceptionBean;
    void updateDocEmitidoAclaratoria(long solicitudId, String flgNuevo, String flgAntiguo) throws ExceptionBean;
    void saveExtTbSubsanacion(ExtTbSubsanacionBean extTbSubsanacionBean) throws ExceptionBean;
    void solicitarDesistimiento(long solicitudId, String usr) throws ExceptionBean;
    List<String> getCodRgst(String anio, long nroPubl) throws ExceptionBean;
    long saveMovimiento(MovimientoBean movimientoBean) throws ExceptionBean;
    long getPersonaIdByLineaPrepagoId(long lineaPrepagoId) throws ExceptionBean;
    long saveAbono(AbonoBean abonoBean) throws ExceptionBean;
    long saveComprobante(ComprobanteBean comprobanteBean) throws ExceptionBean;
    long savePagoSolicitud(PagoSolicitudBean pagoSolicitudBean) throws ExceptionBean;
    ReciDetaAtenNacBean getReciDetaAtenNac(String aaPubl, long nuPubl) throws ExceptionBean;
    long getCodServicioLiquidacion(String coTipoRgst) throws ExceptionBean;
    long saveReciDetaAtenNac(ReciDetaAtenNacBean reciDetaAtenNacBean) throws ExceptionBean;
    TransaccionBean getTransactionById(String transId) throws ExceptionBean;
    long savePago(RegularizaPagoBean regularizaPagoBean) throws ExceptionBean;
    
    SolicitudDataBean buscarSolicitudxNroPublicidad(String anho, long numero) throws ExceptionBean;
    List<TransaccionBean> getTransactionFromUserNews(String key) throws ExceptionBean;
    String getSolicitudIdNews(String transId) throws ExceptionBean;
    SolicitudDataBean getDataSolicitudNews(String solicitudId) throws ExceptionBean;
}
