package com.example.networkchat;
//created by PugaevDenis
import com.example.networkchat.controllers.ChatController;
import com.example.networkchat.models.Network;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
/*
Задача:
Написать консольный вариант клиент\серверного приложения, в котором пользователь может писать сообщения, как на
клиентской стороне, так и на серверной. Т.е. если на клиентской стороне написать "Привет", нажать Enter то сообщение
должно передаться на сервер и там отпечататься в консоли. Если сделать то же самое на серверной стороне, сообщение
соответственно передается клиенту и печатается у него в консоли. Есть одна особенность, которую нужно учитывать: клиент
или сервер может написать несколько сообщений подряд, такую ситуацию необходимо корректно обработать
*/
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