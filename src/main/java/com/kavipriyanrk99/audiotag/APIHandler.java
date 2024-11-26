package com.kavipriyanrk99.audiotag;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * APIHandler
 */
public class APIHandler {

    private static String accessToken;
    private static String tokenType;

    static String makeHttpRequest(URI requestUri, String token, String contentType, String method,
            BodyPublisher bodyPublisher)
            throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(requestUri)
                .header("Authorization", token)
                .header("Content-Type", contentType)
                .method(method, bodyPublisher)
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        if(response.statusCode() != 200)
            throw new IllegalStateException("[ERROR] Bad API response with status code " + response.statusCode());

        return response.body();
    }

    static void setAccessToken() {
        PropertyLoader.loadProperties();
        try {
            URI requestUri = new URI(PropertyLoader.getAUTH_URL());
            String token = "Basic " + Base64.getEncoder().encodeToString(new String(PropertyLoader.getCLIENT_ID() + ":" + PropertyLoader.getCLIENT_SECRET()).getBytes());
            String contentType = "application/x-www-form-urlencoded";
            String method = "POST";
            BodyPublisher bodyPublisher = BodyPublishers.ofString("grant_type=client_credentials");
            String responseData = makeHttpRequest(requestUri, token, contentType, method, bodyPublisher);

            if(responseData == null || responseData.isEmpty())
                throw new IllegalStateException("[ERROR] Invalid reponse data");

            JSONObject data = new JSONObject(responseData);
            accessToken = (String)data.get("access_token");
            tokenType = (String)data.get("token_type");
        } catch (URISyntaxException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            System.out.println(e);
        } catch (IllegalStateException e) {
            System.out.println(e);
        } catch (JSONException e) {
            System.out.println(e);
        }
    }

	public static String getAccessToken() {
		return accessToken;
	}

	public static String getTokenType() {
		return tokenType;
	}
}
