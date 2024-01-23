package pe.gob.sunarp.app.solicitud.restclient.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.sunarp.app.solicitud.config.ApplicationProperties;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;
import pe.gob.sunarp.app.solicitud.exception.ExceptionProperties;
import pe.gob.sunarp.app.solicitud.request.DescargaCertificadoRequest;
import pe.gob.sunarp.app.solicitud.response.DescargarCertificadoResponse;
import pe.gob.sunarp.app.solicitud.restclient.DescargaCertificadoRestClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class DescargaCertificadoRestClientImpl implements DescargaCertificadoRestClient {

    private static final Logger LOG = LoggerFactory.getLogger(DescargaCertificadoRestClientImpl.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ExceptionProperties exceptionProperties;

    @Override
    public DescargarCertificadoResponse descargarCertificado(DescargaCertificadoRequest request) throws ExceptionBean {
        DescargarCertificadoResponse response = new DescargarCertificadoResponse();
        URL url;
        try {
            url = new URL(applicationProperties.getDescargaCertiUrl());
            LOG.info("url = " + url);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = new Gson().toJson(request, DescargaCertificadoRequest.class);
            LOG.info("input = " + input);
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                ObjectMapper mapper= new ObjectMapper();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response = mapper.readValue(inputLine, DescargarCertificadoResponse.class);
                }
                in.close();
                LOG.info("code = " + response.getCode());
                if(response.getCode().equals("000")){
                    String fileName = request.getAnioPubl().concat("-")
                            .concat(request.getNumPubl()).concat("-")
                            .concat(request.getCodigoTipoEsquela())
                            .concat(".pdf");
                    response.setFileName(fileName);
                }
            } else {
                conn.disconnect();
                ExceptionBean ex = exceptionProperties.getDownloadFailed();
                ex.setDescription(ex.getDescription().concat(String.valueOf(responseCode)));
                throw ex;
            }
            conn.disconnect();
        } catch (Exception e) {
            response.setCode("888");
            response.setErrorMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }
}
