package com.ciphersentinel.client;

import com.ciphersentinel.client.model.API;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientController implements Initializable
{
    @FXML
    private VBox vaultVBox;

    @FXML
    protected void onCloseButtonClick()
    {
        Platform.exit();
    }

    @FXML
    protected void onMinimizeButtonClick()
    {
        // Get the stage from any node in the scene graph
        Stage stage = (Stage) vaultVBox.getScene().getWindow();

        // Minimize the stage
        stage.setIconified(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        loadVault();
    }

    private void loadVault()
    {
        // Perform Request
        var response = performVaultGETRequest(API.VAULT.getUrl(), 1);

        // Check if the response is successful before proceeding
        if (response == null || response.statusCode() != 200)
        {
            System.out.println("Failed to retrieve vault data.");
            return;
        }

        // Create FXML Vault items
        try
        {
            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert JSON string to Map
            Map<String, Object> map = objectMapper.readValue(response.body(), Map.class);

            // For every Credential Pair:
            for (String key : map.keySet())
            {
                // Load FXML file for each vault item
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ClientApplication.class.getResource("list-item.fxml"));
                Parent node = loader.load();

                // Retrieve controller associated with the loaded FXML file
                VaultItemController vaultItemController = loader.getController();

                // Set data
                vaultItemController.setData(key, map.get(key).toString());

                // Create Listeners
                node.setOnMouseEntered(v -> node.setStyle("-fx-background-color: #165DDB; -fx-background-radius: 10px"));
                node.setOnMouseExited(v -> node.setStyle("-fx-background-color: #2C323B"));

                // Add items to VBox
                vaultVBox.getChildren().add(node);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }



        // LOG
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());

    }

    private HttpResponse<String> performVaultGETRequest(String route, int id)
    {
        HttpResponse<String> response = null;

        try
        {
            // Create HttpClient
            HttpClient httpClient = HttpClient.newBuilder().build();

            // Create GET URI
            URI uri = URI.create(route + "?userId=" + id);

            // Create HttpRequest with GET method
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .GET()
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
