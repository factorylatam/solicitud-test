package pe.gob.sunarp.app.solicitud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.gob.sunarp.app.solicitud.bean.SgmtSolicitudBean;
import pe.gob.sunarp.app.solicitud.bean.SolicitudBean;
import pe.gob.sunarp.app.solicitud.bean.TransaccionBean;
import pe.gob.sunarp.app.solicitud.request.AclararSolicitudRequest;
import pe.gob.sunarp.app.solicitud.request.PagarLiquidacionRequest;
import pe.gob.sunarp.app.solicitud.request.SubsanarSolicitudRequest;
import pe.gob.sunarp.app.solicitud.response.GetTransaccionResponse;

import java.time.LocalDateTime;

import static pe.gob.sunarp.app.solicitud.util.Constantes.*;

@Mapper(componentModel = "spring" )
public interface SgmtSolicitudMapper {

    @Mapping(target = "estadoInicial", source = "solicitud.estado")
    @Mapping(target = "estadoFinal", constant = POR_VERIFICAR)
    @Mapping(target = "tsMovimiento", expression = "java(getDate())")
    @Mapping(target = "usrMovimiento", source = "aclararSolicitudRequest.usuario")
    @Mapping(target = "flgrev", constant = FLG_1)
    @Mapping(target = "comentario", source = "aclararSolicitudRequest.detalle")
    @Mapping(target = "tsModi", expression = "java(getDate())")
    @Mapping(target = "usrModi", source = "aclararSolicitudRequest.usuario")
    @Mapping(target = "solicitante", source = "aclararSolicitudRequest.solicitudId")
    @Mapping(target = "solicitudId", source = "aclararSolicitudRequest.solicitudId")
    SgmtSolicitudBean toSgmtSolicitudBeanFromAclaratoria(AclararSolicitudRequest aclararSolicitudRequest, SolicitudBean solicitud);

    @Mapping(target = "estadoInicial", source = "solicitud.estado")
    @Mapping(target = "estadoFinal", constant = POR_VERIFICAR)
    @Mapping(target = "tsMovimiento", expression = "java(getDate())")
    @Mapping(target = "usrMovimiento", source = "subsanarSolicitudRequest.usuario")
    @Mapping(target = "flgrev", constant = FLG_0)
    @Mapping(target = "solicitudId", source = "subsanarSolicitudRequest.solicitudId")
    SgmtSolicitudBean toSgmtSolicitudBeanFromSubsanar(SubsanarSolicitudRequest subsanarSolicitudRequest, SolicitudBean solicitud);


    @Mapping(target = "flgrev", constant = FLG_0)
    @Mapping(target = "estadoInicial", constant = LIQUIDADO)
    @Mapping(target = "estadoFinal", constant = POR_VERIFICAR)
    @Mapping(target = "solicitudId", source = "pagarLiquidacionRequest.solicitudId")
    @Mapping(target = "tsMovimiento", expression = "java(getDate())")
    @Mapping(target = "usrMovimiento", source = "pagarLiquidacionRequest.usuario")
    


    SgmtSolicitudBean toSgmtSolicitudBeanFromPagarLiquidacion(PagarLiquidacionRequest pagarLiquidacionRequest);

    default LocalDateTime getDate(){
        return LocalDateTime.now();
    }

    
}
