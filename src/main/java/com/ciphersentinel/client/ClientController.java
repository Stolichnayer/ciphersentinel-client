package com.ciphersentinel.client;

import com.ciphersentinel.client.model.API;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientController
{
    @FXML
    private Label testLabel;
    @FXML
    protected void onCloseButtonClick()
    {
        //Platform.exit();
        initialize();
    }

    private void initialize()
    {
        // perform Request
        var response = performGetPasswordListRequest(API.VAULT.getUrl() + "?userId=", 1);


        testLabel.setText(response.body());
        // LOG
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());

    }

    private HttpResponse<String> performGetPasswordListRequest(String url, int id)
    {
        HttpResponse<String> response = null;

        try
        {
            // Create POST parameters and URL encode them
            String postData = "userId=" + 1;

            // Create HttpClient
            HttpClient httpClient = HttpClient.newBuilder().build();

            // Create HttpRequest with POST method and POST data
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(postData))
                    .build();

            // Send the request and handle the response
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return response;

    }
}
