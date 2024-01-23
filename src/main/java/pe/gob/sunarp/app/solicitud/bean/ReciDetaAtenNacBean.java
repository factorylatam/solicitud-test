package pe.gob.sunarp.app.solicitud.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReciDetaAtenNacBean {
    private long reciDetaAteNacId;
    private String aaReciTmp;
    private String coZonaRegiDest;
    private String coOficRegiDest;
    private BigDecimal subtotal;
    private String coTipoServ;
    private String coTipoRgst;
    private String inResuAsie;
    private String nuPartPred;
    private String nuAsieSele;
    private String tiDocuIden;
    private String nuDocuIden;
    private String apePate;
    private String apeMate;
    private String noPers;
    private String coLibr;
    private String nuPart;
    private String nuFich;
    private String nuTomo;
    private String nuFoli;
    private String nuOrigPart;
    private String imPagi;
    private BigDecimal noPagi;
    private BigDecimal toPagi;
    private String idPart;
    private String nuPartRpv;
    private String nuPlac;
    private String coZonaRegiPart;
    private String coOficRegiPart;
    private String inAmbtNaci;
    private BigDecimal caCopi;
    private BigDecimal coServRgst;
    private String inEstd;
    private LocalDateTime tsCrea;
    private long solicitudId;


    private String imPagiClob;
    private String nuAsieSeleClob;

    private String anPubl;
    private long nuPubl;

    private String inTipoSoli;
}
