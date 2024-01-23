package pe.gob.sunarp.app.solicitud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.gob.sunarp.app.solicitud.bean.ExtTbSubsanacionBean;
import pe.gob.sunarp.app.solicitud.bean.SolicitudBean;
import pe.gob.sunarp.app.solicitud.request.SubsanarSolicitudRequest;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring" )
public interface ExtTbSubsanacionMapper {

    @Mapping(target = "solicitudId", source = "solicitud.solicitudId")
    @Mapping(target = "dessub", source = "subsanarSolicitud.detalle")
    @Mapping(target = "usrcre", source = "subsanarSolicitud.usuario")
    @Mapping(target = "usrsub", source = "subsanarSolicitud.usuario")
    @Mapping(target = "dateSub", expression = "java(getDate())")
    
    @Mapping(target = "nobsCodigo", expression = "java(getDato3())")
    @Mapping(target = "fecrea", expression = "java(getDato())")
    @Mapping(target = "doc", expression = "java(getDato2())")

    ExtTbSubsanacionBean toExtTbSubsanacionFromSubsanar(SubsanarSolicitudRequest subsanarSolicitud, SolicitudBean solicitud);

    default LocalDateTime getDate(){
        return LocalDateTime.now();
    }
    
    default String getDato() {
    	return "";
    }
    

    default byte[] getDato2() {
    	return null;
    }
    default Long getDato3() {
    	return null;
    }
    
}
