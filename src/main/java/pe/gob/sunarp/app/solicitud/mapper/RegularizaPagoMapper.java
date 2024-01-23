package pe.gob.sunarp.app.solicitud.mapper;

import org.mapstruct.Mapper;
import pe.gob.sunarp.app.solicitud.bean.RegularizaPagoBean;
import pe.gob.sunarp.app.solicitud.request.PagarLiquidacionRequest;

@Mapper(componentModel = "spring" )
public interface RegularizaPagoMapper {
    RegularizaPagoBean toRegularizaPagoBean(PagarLiquidacionRequest pagarLiquidacionRequest);
}
