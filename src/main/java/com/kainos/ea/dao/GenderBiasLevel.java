package com.kainos.ea.dao;

import com.kainos.ea.models.BiasRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GenderBiasLevel {

    public BiasRequest getGenderBias(String request) {
        try {
            URL url = new URL("https://wydwyiie5k.execute-api.eu-west-2.amazonaws.com/Prod/gender-bias-prediction");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
                    (conn.getOutputStream())));

            wr.write(request);

            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            conn.disconnect();



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
