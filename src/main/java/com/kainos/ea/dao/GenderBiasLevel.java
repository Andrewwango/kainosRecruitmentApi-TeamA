package com.kainos.ea.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kainos.ea.models.BiasRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GenderBiasLevel {

    public BiasRequest getGenderBias(@JsonProperty String request) throws IOException {
        try {
            URL url = new URL("https://74fc2jchyl.execute-api.eu-west-2.amazonaws.com/Prod/gender-bias-prediction");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");

            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = request.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            output = br.readLine();
            conn.disconnect();
            List<String> biased_words_male = new ArrayList<>();
            List<String> biased_words_female = new ArrayList<>();
            JSONObject obj = new JSONObject(output);
            Float percentage_bias = obj.getFloat("percentage_bias");
            JSONArray male = obj.getJSONObject("biased_words").getJSONArray("biased_words_male");
            JSONArray female = obj.getJSONObject("biased_words").getJSONArray("biased_words_female");
            for (int i = 0; i < male.length(); i++)
            {
                biased_words_male.add(" " + male.getString(i));
            }
            for (int i = 0; i < female.length(); i++)
            {
                biased_words_female.add(" " + female.getString(i));
            }
            float roundedBias = (float) Math.round(percentage_bias * 10)/10;
            String percentage_bias_accurate = roundedBias + "%";

            BiasRequest genderBias = new BiasRequest(percentage_bias_accurate, biased_words_male, biased_words_female);
            System.out.println(genderBias);

            return genderBias;

        }  catch (IOException e) {
            throw e;
        }
    }
}
