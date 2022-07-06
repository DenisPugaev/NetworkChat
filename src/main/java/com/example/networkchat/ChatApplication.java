package com.example.networkchat;
//created by PugaevDenis

import com.example.networkchat.controllers.ChatController;
import com.example.networkchat.models.Network;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class ChatApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChatApplication.class.getResource("chat-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMinWidth(760);
        stage.setMinHeight(450);
        stage.setTitle("Chat");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setAlwaysOnTop(true);
        stage.show();

        Network network = new Network();
        ChatController chatController = fxmlLoader.getController();
        chatController.setNetwork(network);

        network.connect();
        network.waitMessage(chatController);

    }

    public static void main(String[] args) {
        launch();
    }
}