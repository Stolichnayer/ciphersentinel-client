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

public class VaultItemController
{
    @FXML
    private Label appNameItem;
    @FXML
    private Label usernameItem;

    public void setData(String appName, String username)
    {
        appNameItem.setText(appName);
        usernameItem.setText(username);
    }
}