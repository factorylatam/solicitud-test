package pe.gob.sunarp.app.solicitud.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class SolicitudDataBean  implements Serializable {
    private String solicitudId;
    private String userKeyId;
    private String ncopAdic;
    private String nsolMonLiq;
    private String tsDesis;
    private String tsMaxReing;

    //flags
    private String flgObs;
    private String flgEstado;
    private String flgLiq;
    private String flgTipoLiq;
    private String flgDesis;
    private String flgDeneg;
    private String flgAban;
    private String flgAclar;

    //Estado
    private String estado;
    private String codEstado;

    //Certificado
    private String certificadoId;
    private String tpoCertificado;
    private String aaPubl;
    private String nuPubl;
    private String oficina;
    private String codOficina;
    private String codZona;
    private String numPartida;
    private String asiento;
    private String annoTitulo;
    private String numTitulo;
    private String numPaginas;
    private String acto;
    private String tpoPersCertif;
    private String nombrePersCertif;
    private String apePatPersCertif;
    private String apeMatPersCertif;
    private String razonSocialPersCertif;
    private String areaRegistral;

    //solicitante
    private String tpoPersSol;
    private String nombrePersSol;
    private String apePatPersSol;
    private String apeMatPersSol;
    private String razonSocialPersSol;
    private String tpoDoc;
    private String numDoc;

    //Destinatario
    private String tpoEnvio;
    private String tpoEnvioDesc;
    private String oficinaDest;
    private String distrito;
    private String direccion;
    private String codPostal;
    private String departamento;
    private String provincia;
    private String tpoPersDest;
    private String nombrePersDest;
    private String apePatPersDest;
    private String apeMatPersDest;
    private String razonSocialPersDest;

    //pago
    private String monto;
    private String fecha;
    private String tpoPago;
    private String mayorDerecho;
    private String codTpoEsquela;

}
