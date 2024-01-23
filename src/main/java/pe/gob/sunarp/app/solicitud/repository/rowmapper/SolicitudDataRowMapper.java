package pe.gob.sunarp.app.solicitud.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.sunarp.app.solicitud.bean.SolicitudDataBean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SolicitudDataRowMapper implements RowMapper<SolicitudDataBean> {

    @Override
    public SolicitudDataBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        return SolicitudDataBean.builder()
                .solicitudId(rs.getString("SOLICITUD_ID"))
                .userKeyId(rs.getString("USER_KEY_ID"))
                .ncopAdic(rs.getString("NCOP_ADIC"))
                .nsolMonLiq(rs.getString("NSOL_MONTLIQ"))
                .tsDesis(rs.getString("TS_DESIS"))
                .tsMaxReing(rs.getString("TS_MAX_REING"))
                .flgObs(rs.getString("CSOL_FLGOBS"))
                .flgEstado(rs.getString("CSOL_ESTADO"))
                .flgLiq(rs.getString("CSOL_FLGLIQ"))
                .flgTipoLiq(rs.getString("CSOL_FLGTIPOLIQ"))
                .flgDesis(rs.getString("CSOL_FLGDESIS"))
                .flgDeneg(rs.getString("CSOL_FLGDENEG"))
                .flgAban(rs.getString("CSOL_FLGABAN"))
                .flgAclar(rs.getString("CSOL_FLGACLAR"))
                .estado(rs.getString("MENSAJE_USUARIO"))
                .codEstado(rs.getString("ESTADO"))
                .certificadoId(rs.getString("CERTIFICADO_ID"))
                .tpoCertificado(rs.getString("TPO_CERT"))
                .aaPubl(rs.getString("AA_PUBL"))
                .nuPubl(rs.getString("NU_PUBL"))
                .oficina(rs.getString("OFIC_REG"))
                .codOficina(rs.getString("OFIC_REG_ID"))
                .codZona(rs.getString("REG_PUB_ID"))
                .numPartida(rs.getString("NUM_PARTIDA"))
                .asiento(rs.getString("NS_ASIENTO"))
                .annoTitulo(rs.getString("AA_TITU"))
                .numTitulo(rs.getString("NUM_TITU"))
                .numPaginas(rs.getString("NUMPAG"))
                .acto(rs.getString("ACTO"))
                .tpoPersCertif(rs.getString("TPO_PERS"))
                .nombrePersCertif(rs.getString("NOMBRES"))
                .apePatPersCertif(rs.getString("APE_PAT"))
                .apeMatPersCertif(rs.getString("APE_MAT"))
                .razonSocialPersCertif(rs.getString("RAZ_SOC"))
                .areaRegistral(rs.getString("AREA_REG"))
                .tpoPersSol(rs.getString("TPO_PERS_SOL"))
                .nombrePersSol(rs.getString("NOMBRES_SOL"))
                .apePatPersSol(rs.getString("APE_PAT_SOL"))
                .apeMatPersSol(rs.getString("APE_MAT_SOL"))
                .razonSocialPersSol(rs.getString("RAZ_SOC_SOL"))
                .tpoDoc(rs.getString("TIPO_DOC_SOL"))
                .numDoc(rs.getString("NUM_DOC_IDEN"))
                .tpoEnvio(rs.getString("TPO_ENV"))
                .tpoEnvioDesc(rs.getString("TPO_ENV_DESC"))
                .oficinaDest(rs.getString("OFIC_REG_DES"))
                .distrito(rs.getString("DISTRITO"))
                .direccion(rs.getString("DIRECC"))
                .codPostal(rs.getString("COD_POST"))
                .departamento(rs.getString("DPTO"))
                .provincia(rs.getString("PROV"))
                .tpoPersDest(rs.getString("TPO_PERS_DES"))
                .nombrePersDest(rs.getString("NOMBRES_DES"))
                .apePatPersDest(rs.getString("APE_PAT_DES"))
                .apeMatPersDest(rs.getString("APE_MAT_DES"))
                .razonSocialPersDest(rs.getString("RAZ_SOC_DES"))
                .monto(rs.getString("TOTAL"))
                .fecha(rs.getString("TS_CREA"))
                .tpoPago(rs.getString("TPO_PAGO"))
                .mayorDerecho(rs.getString("MAYOR_DERECHO"))
                .codTpoEsquela(rs.getString("TIPO_ESQUELA"))
                .build();
    }
}
