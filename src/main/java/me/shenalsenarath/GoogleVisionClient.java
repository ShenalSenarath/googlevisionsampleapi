package me.shenalsenarath;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author ssenarath
 */
public class GoogleVisionClient {
    private String apiKey;
    private static GoogleVisionClient instance;
    private final String reqUrl = "https://vision.googleapis.com/v1/images:annotate?fields=responses%2FtextAnnotations%2Fdescription&key=";
    Client restClient = Client.create();

    private GoogleVisionClient() {
        apiKey = "AIzaSyAfVA_778222_1KIyeyMNj7H_Zojkr6EvA";
    }

    public static GoogleVisionClient getInstance() {
        if (instance == null) {
            instance = new GoogleVisionClient();
        }
        return instance;
    }

    public String ocrImage(String Base64ImageString) {
        WebResource webResource = restClient.resource(reqUrl + apiKey);



        ClientResponse response = webResource.type("application/json")
                .post(ClientResponse.class, getJsonString(Base64ImageString));

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }


        String output = response.getEntity(String.class);

        JSONObject responseJsonObject= new JSONObject(output);
        String fullText;
        try {
             fullText = responseJsonObject.getJSONArray("responses")
                    .getJSONObject(0).getJSONArray("textAnnotations")
                    .getJSONObject(0).getString("description");
        }catch (JSONException e){
            System.out.println("No results found");
            return "";
        }
        return fullText;
    }


    private String getJsonString(String Base64ImageString) {
        String jsonString = "{\n" +
                "    \"requests\": [\n" +
                "        {\n" +
                "            \"image\": {\n" +
                "                \"content\": \"" + Base64ImageString + "\"\n" +
                "            },\n" +
                "            \"features\": [\n" +
                "                {\n" +
                "                    \"maxResults\": 1000,\n" +
                "                    \"type\": \"TEXT_DETECTION\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        return jsonString;

    }


}
