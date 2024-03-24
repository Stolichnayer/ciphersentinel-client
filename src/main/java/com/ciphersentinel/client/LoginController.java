package com.ciphersentinel.client;

import com.ciphersentinel.client.model.API;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;

public class LoginController
{
    @FXML
    private Label loginInformationLabel;
    @FXML
    private TextField loginUsernameTextfield;
    @FXML
    private TextField loginPasswordTextfield;

    @FXML
    protected void onLoginButtonClick()
    {
        // Make incorrect label invisible in case it's visible
        loginInformationLabel.setVisible(false);

        // perform Request
        var response = performLoginRequest(API.LOGIN.getUrl(),
                loginUsernameTextfield.getText(), loginPasswordTextfield.getText());

        if (response == null)
        {
            loginInformationLabel.setText("An unknown error occurred. Please try again.");
            loginInformationLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (response.statusCode() == 200)
        {
            loginInformationLabel.setText("Login successful!");
            loginInformationLabel.setStyle("-fx-text-fill: green;");
            loadClient();
        }
        else if (response.statusCode() == 401)
        {
            loginInformationLabel.setText("Incorrect username or password.");
            loginInformationLabel.setStyle("-fx-text-fill: red;");
        }
        else
        {
            loginInformationLabel.setText("An error occurred. Please try again. (Error Code: " + response.statusCode() + ")");
            loginInformationLabel.setStyle("-fx-text-fill: red;");
        }

        loginInformationLabel.setVisible(true);

        // LOG
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());

    }

    private void loadClient()
    {
        // Get current stage
        Stage stage = (Stage) loginInformationLabel.getScene().getWindow();

        try
        {
            // Load client FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("client-view.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Set the new scene to the stage
            stage.setScene(scene);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private HttpResponse<String> performLoginRequest(String url, String username, String password)
    {
        HttpResponse<String> response = null;

        try
        {
            // Create POST parameters and URL encode them
            String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8);
            String encodedPassword = URLEncoder.encode(password, StandardCharsets.UTF_8);
            String postData = "username=" + encodedUsername + "&password=" + encodedPassword;

            // Create HttpClient
            HttpClient httpClient = HttpClient.newBuilder().build();

            // Create HttpRequest with POST method and POST data
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(postData))
                    .build();

            // Send the request and handle the response
            response = httpClient.send(request, BodyHandlers.ofString());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return response;
    }
}