package com.ciphersentinel.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ClientApplication extends Application
{
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("client-view.fxml"));
        Parent root = fxmlLoader.load();

        // Set the stage style to transparent with no decorations
        stage.initStyle(StageStyle.TRANSPARENT);

        // Create the scene
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());


        // Set initial values for xOffset and yOffset
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);

        // Define the top area where dragging is allowed (e.g., top 40 pixels)
        final double TOP_DRAG_HEIGHT = 40;

        // Flag to indicate whether dragging is allowed
        AtomicBoolean draggingAllowed = new AtomicBoolean(false);

        // Set event handlers for dragging the window
        root.setOnMousePressed(event -> {
            if (event.getY() < TOP_DRAG_HEIGHT) { // Check if mouse is within the top area
                xOffset.set(event.getSceneX());
                yOffset.set(event.getSceneY());
                draggingAllowed.set(true); // Start dragging
            }
        });

        root.setOnMouseDragged(event -> {
            if (draggingAllowed.get()) {
                double newX = event.getScreenX() - xOffset.get();
                double newY = event.getScreenY() - yOffset.get();

                stage.setX(newX);
                stage.setY(newY);
            }
        });

        root.setOnMouseReleased(event -> {
            draggingAllowed.set(false); // Stop dragging
        });

        // Set the scene
        stage.setScene(scene);
        stage.setTitle("CipherSentinel");
        stage.show();
    }


    public static void main(String[] args)
    {
        launch();
    }
}