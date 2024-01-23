package pe.gob.sunarp.app.solicitud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pe.gob.sunarp.app.solicitud.bean.TransaccionBean;
import pe.gob.sunarp.app.solicitud.response.GetTransaccionResponse;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    GetTransaccionResponse toGetTransaccionResponse(TransaccionBean transaccionBean);
}
