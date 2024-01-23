package pe.gob.sunarp.app.solicitud.restclient.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.sunarp.app.solicitud.bean.GenericResponseBean;
import pe.gob.sunarp.app.solicitud.bean.ReingresoRequestBean;
import pe.gob.sunarp.app.solicitud.config.ApplicationProperties;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;
import pe.gob.sunarp.app.solicitud.exception.ExceptionProperties;
import pe.gob.sunarp.app.solicitud.restclient.CopiaLiteralVirtualRestClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CopiaLiteralVirtualRestClientImpl implements CopiaLiteralVirtualRestClient {

    private static final Logger LOG = LoggerFactory.getLogger(CopiaLiteralVirtualRestClientImpl.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ExceptionProperties exceptionProperties;

    @Override
    public GenericResponseBean generarReingresoPublicidad(ReingresoRequestBean reingresoRequestBean) throws ExceptionBean {
        URL url;
        LOG.info("generarReingresoPublicidad");
        GenericResponseBean genericResponse = new GenericResponseBean();
        try {
        	
            url = new URL(applicationProperties.getReingresoUrl());
            LOG.info("url = " + url);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = new Gson().toJson(reingresoRequestBean);
            LOG.info("input = " + input);
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes("UTF-8"));
            os.flush();

            ObjectMapper mapper= new ObjectMapper();
            int responseCode = conn.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    genericResponse = mapper.readValue(inputLine, GenericResponseBean.class);
                }
                in.close();
            } else {
                conn.disconnect();
                LOG.error("responseCode = " + responseCode);
                ExceptionBean ex = exceptionProperties.getReEntryFailed();
                ex.setDescription(ex.getDescription().concat(String.valueOf(responseCode)));
                throw ex;
            }
            conn.disconnect();
        } catch (Exception e) {
            genericResponse.setCode("888");
            genericResponse.setErrorMessage(e.getMessage());
            LOG.error("ERROR:"+e.getMessage());
            // e.printStackTrace();
        }
        return genericResponse;
    }
}
