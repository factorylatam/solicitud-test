package pe.gob.sunarp.app.solicitud.bean;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DatosBoletaRPV {
    private String usuario;
    private String codZona;
    private String codOficina;
    private String placa;
    private BigDecimal costo;
    private String ip;
    private PartidaDirectaDetBean datosBoletaRPV;

    private Integer idUser;
    private String idUnico;
    private String pan;
    private String dscCodAccion;
    private String codAutoriza;
    private String codtienda;
    private String numOrden;
    private String codAccion;
    private String fechaYhoraTx;
    private String nomEmisor;
    private String oriTarjeta;
    private String respuesta;
    private Integer transId;
    private String concepto;
    private String eticket;

    private VisanetResponseBean visanetResponse;
}
