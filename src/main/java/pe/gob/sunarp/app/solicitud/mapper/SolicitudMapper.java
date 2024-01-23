package pe.gob.sunarp.app.solicitud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.gob.sunarp.app.solicitud.bean.SolicitudBean;
import pe.gob.sunarp.app.solicitud.bean.SolicitudDataBean;
import pe.gob.sunarp.app.solicitud.response.GetSolicitudResponse;

import static pe.gob.sunarp.app.solicitud.util.Constantes.N;
import static pe.gob.sunarp.app.solicitud.util.Constantes.SPACE;

@Mapper(componentModel = "spring")
public interface SolicitudMapper {

    @Mapping(target = "flags.flgObs", source = "flgObs")
    @Mapping(target = "flags.flgEstado", source = "flgEstado")
    @Mapping(target = "flags.flgLiq", source = "flgLiq")
    @Mapping(target = "flags.flgTipoLiq", source = "flgTipoLiq")
    @Mapping(target = "flags.flgDesis", source = "flgDesis")
    @Mapping(target = "flags.flgDeneg", source = "flgDeneg")
    @Mapping(target = "flags.flgAban", source = "flgAban")
    @Mapping(target = "flags.flgAclar", source = "flgAclar")
    @Mapping(target = "estado.estado", source = "estado")
    @Mapping(target = "estado.codEstado", source = "codEstado")
    @Mapping(target = "certificado.certificadoId", source = "certificadoId")
    @Mapping(target = "certificado.tpoCertificado", source = "tpoCertificado")
    @Mapping(target = "certificado.aaPubl", source = "aaPubl")
    @Mapping(target = "certificado.nuPubl", source = "nuPubl")
    @Mapping(target = "certificado.oficina", source = "oficina")
    @Mapping(target = "certificado.codOficina", source = "codOficina")
    @Mapping(target = "certificado.codZona", source = "codZona")
    @Mapping(target = "certificado.numPartida", source = "numPartida")
    @Mapping(target = "certificado.asiento", source = "asiento")
    @Mapping(target = "certificado.annoTitulo", source = "annoTitulo")
    @Mapping(target = "certificado.numTitulo", source = "numTitulo")
    @Mapping(target = "certificado.numPaginas", source = "numPaginas")
    @Mapping(target = "certificado.acto", source = "acto")
    @Mapping(target = "certificado.tpoPers", source = "tpoPersCertif")
    @Mapping(target = "certificado.areaRegistral", source = "areaRegistral")
    @Mapping(target = "certificado.nombrePers", expression = "java(buildName(solicitudDataBean.getTpoPersCertif(), " +
            "solicitudDataBean.getNombrePersCertif(), " +
            "solicitudDataBean.getApePatPersCertif(), " +
            "solicitudDataBean.getApeMatPersCertif(), " +
            "solicitudDataBean.getRazonSocialPersCertif() ))")
    @Mapping(target = "solicitante.tpoDoc", source = "tpoDoc")
    @Mapping(target = "solicitante.numDoc", source = "numDoc")
    @Mapping(target = "solicitante.nombre", expression = "java(buildName(solicitudDataBean.getTpoPersSol(), " +
            "solicitudDataBean.getNombrePersSol(), " +
            "solicitudDataBean.getApePatPersSol(), " +
            "solicitudDataBean.getApeMatPersSol(), " +
            "solicitudDataBean.getRazonSocialPersSol() ))")
    @Mapping(target = "destinatario.tpoEnvio", source = "tpoEnvio")
    @Mapping(target = "destinatario.tpoEnvioDesc", source = "tpoEnvioDesc")
    @Mapping(target = "destinatario.oficina", source = "oficinaDest")
    @Mapping(target = "destinatario.distrito", source = "distrito")
    @Mapping(target = "destinatario.direccion", source = "direccion")
    @Mapping(target = "destinatario.codPostal", source = "codPostal")
    @Mapping(target = "destinatario.departamento", source = "departamento")
    @Mapping(target = "destinatario.provincia", source = "provincia")
    @Mapping(target = "destinatario.nombre", expression = "java(buildName(solicitudDataBean.getTpoPersDest(), " +
            "solicitudDataBean.getNombrePersDest(), " +
            "solicitudDataBean.getApePatPersDest(), " +
            "solicitudDataBean.getApeMatPersDest(), " +
            "solicitudDataBean.getRazonSocialPersDest() ))")
    @Mapping(target = "pago.monto", source = "monto")
    @Mapping(target = "pago.fecha", source = "fecha")
    @Mapping(target = "pago.tpoPago", source = "tpoPago")
    @Mapping(target = "pago.mayorDerecho", source = "mayorDerecho")
    @Mapping(target = "pago.codTpoEsquela", source = "codTpoEsquela")
    @Mapping(target = "sistema", constant = "SPRL")
    
    @Mapping(target = "solicitudId", source = "solicitudId")
    @Mapping(target = "userKeyId", source = "userKeyId")
    
    GetSolicitudResponse toGetSolicitudResponse (SolicitudDataBean solicitudDataBean);

    default String buildName(String tipo, String nombre, String apePat, String apeMat, String razSoc) {
        return tipo.equals(N) ? nombre.concat(SPACE).concat(apePat).concat(SPACE).concat(apeMat).trim() : razSoc.trim();
    }
}
