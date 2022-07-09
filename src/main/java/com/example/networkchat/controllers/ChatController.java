package com.example.networkchat.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.networkchat.models.Network;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import static javafx.collections.FXCollections.observableArrayList;


public class ChatController {
    private String nickName = "You";
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    @FXML
    private Label usernameTitle;
    @FXML
    private ListView<String> userList;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textInputField;
    private Network network;

    @FXML
    public void sendMessage() {
        String message = textInputField.getText();
        if (!textInputField.getText().isEmpty()) {
            addMessage(message);
            textInputField.clear();
            if (message.isEmpty()) {
                return;
            }
            network.sendMessage(message);
        }
    }

    public void addMessage(String message) {


//        textArea.appendText(nickName + " (" + dtf.format(now) + "): \n" + message + "\n");
        textArea.setText(new StringBuilder(textArea.getText()).insert(0, nickName + " (" + dtf.format(now) + "): \n" + message + "\n").toString());
        System.lineSeparator();
    }

    public void handleEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            sendMessage();
        }
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void addMessageServer(String message) {

        textArea.setText(new StringBuilder(textArea.getText()).insert(0, "Сервер: " + " (" + dtf.format(now) + "): \n" + message + "\n").toString());
        System.lineSeparator();
    }

    @FXML
    void initialize() {
        textArea.setEditable(false);
        userList.setItems(observableArrayList("Пользователь1", "Пользователь2", "Пользователь3", "Пользователь4", "Пользователь5"));

    }

}

