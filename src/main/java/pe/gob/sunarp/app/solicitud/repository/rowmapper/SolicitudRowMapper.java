package pe.gob.sunarp.app.solicitud.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.sunarp.app.solicitud.bean.SolicitudBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static pe.gob.sunarp.app.solicitud.util.Constantes.BD_DATE;

public class SolicitudRowMapper implements RowMapper<SolicitudBean> {

    @Override
    public SolicitudBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        return SolicitudBean.builder()
                .solicitudId(rs.getString("SOLICITUD_ID"))
                .estado(rs.getString("ESTADO"))
                .userKeyId(rs.getString("USER_KEY_ID"))
                .csolflgaban(rs.getString("CSOL_FLGABAN"))
                .csolFlgdeneg(rs.getString("CSOL_FLGDENEG"))
                .csolFlgliq(rs.getString("CSOL_FLGLIQ"))
                .csolEstado(rs.getString("CSOL_ESTADO"))
                .csolFlgobs(rs.getString("CSOL_FLGOBS"))
                .csolFlgrec(rs.getString("CSOL_FLGREC"))
                .csolFlgAclar(rs.getString("CSOL_FLGACLAR"))
                .csolFlgDesis(rs.getString("CSOL_FLGDESIS"))
                .csolFlgTipoLiq(rs.getString("CSOL_FLGTIPOLIQ"))
                .nsolMonLiq(rs.getString("NSOL_MONTLIQ"))
                .ncopAdic(rs.getString("NCOP_ADIC"))
                .tsDesis(this.convertToLocalDate(rs.getString("TS_DESIS")))
                .tsMaxReing(rs.getString("TS_MAX_REING"))
                .tsModi(this.convertToLocalDate(rs.getString("TS_MODI")))
                .usrModi(rs.getString("USR_MODI"))
                .build();
    }

    private LocalDateTime convertToLocalDate(String dateToConvert) {
        if(Optional.ofNullable(dateToConvert).isPresent()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BD_DATE);
            return LocalDateTime.parse(dateToConvert, formatter);
        } else {
            return null;
        }
    }
}

