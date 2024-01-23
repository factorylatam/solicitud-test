package pe.gob.sunarp.app.solicitud.restclient.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gob.sunarp.app.solicitud.bean.GenerarPublicidadBean;
import pe.gob.sunarp.app.solicitud.config.ApplicationProperties;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;
import pe.gob.sunarp.app.solicitud.exception.ExceptionProperties;
import pe.gob.sunarp.app.solicitud.request.DescargaCertificadoRequest;
import pe.gob.sunarp.app.solicitud.response.DescargarCertificadoResponse;
import pe.gob.sunarp.app.solicitud.response.GuardarCajaResponse;
import pe.gob.sunarp.app.solicitud.restclient.GenerarPublicidadRestClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class GenerarPublicidadRestClientImpl implements GenerarPublicidadRestClient {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ExceptionProperties exceptionProperties;

    @Override
    public GuardarCajaResponse generaPublicidad(GenerarPublicidadBean generarPublicidadBean) throws ExceptionBean {
        GuardarCajaResponse response =  new GuardarCajaResponse();
        URL url;
        try {
            url = new URL(applicationProperties.getGuardarCajaUrl());
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = new Gson().toJson(generarPublicidadBean, GenerarPublicidadBean.class);

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
                    response = mapper.readValue(inputLine, GuardarCajaResponse.class);
                }
                in.close();
            } else {
                response.setCode("999");
            }
            conn.disconnect();
        } catch (Exception e) {
            response.setCode("999");
            response.setErrorMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }
}
