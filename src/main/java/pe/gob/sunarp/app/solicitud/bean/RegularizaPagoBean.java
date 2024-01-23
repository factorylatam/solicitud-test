package pe.gob.sunarp.app.solicitud.bean;

import lombok.Getter;
import lombok.Setter;
import pe.gob.sunarp.app.solicitud.request.NiubizData;

import java.math.BigDecimal;

@Getter
@Setter
public class RegularizaPagoBean {
    private long solicitudId;
    private BigDecimal costoTotal;
    private String ip;
    private String usrId;
    private String codigoGla;

    private String usrKeyId;

//    private Integer idUser;
//    private String idUnico;
//    private String pan;
//    private String dscCodAccion;
//    private String codAutoriza;
//    private String codtienda;
//    private String numOrden;
//    private String codAccion;
//    private String fechaYhoraTx;
//    private String nomEmisor;
//    private String oriTarjeta;
//    private String respuesta;
    private Integer transId;
//    private String eticket;
//    private String concepto;

    private String codCerti;
    private String numeroRecibo;
    private String numeroPublicidad;
    private String codVerificacion;
    private String codLibroOpc;

    // Para Certificado Literal
    private int cantPaginas;
    private int cantPaginasExon;
    private int paginasSolicitadas;
    private int totalPaginasPartidaFicha;
    private String nuAsieSelectSARP;
    private String imPagiSIR;
    private String nuSecuSIR;
    private String codLibro;
    private String coServ;
    private String coTipoRgst;

    private VisanetResponseBean visanetResponse;

    private String lstOtorgantes;
    private String lstApoyos;
    private String lstCuradores;
    private String lstPoderdantes;
    private String lstApoderados;
}
