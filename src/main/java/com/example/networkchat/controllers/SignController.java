package com.example.networkchat.controllers;

import com.example.networkchat.ChatApplication;
import com.example.networkchat.models.Network;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SignController {


    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginReg;

    @FXML
    private TextField passReg;

    @FXML
    private TextField usernameReg;
    private Network network;
    private ChatApplication chatApplication;


    @FXML
    void initialize() {

    }

    @FXML
    void checkAuth(ActionEvent event) {
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();

        if (login.length() == 0 || password.length() == 0) {
            System.out.println("Ошибка ввода при аутентификации");
            System.out.println();
            chatApplication.showErrorAlert("Ошибка ввода при аутентификации", "Поля не должны быть пустыми");

            return;
        }

        if (login.length() > 32 || password.length() > 32) {
            chatApplication.showErrorAlert("Ошибка ввода при аутентификации", "Длина логина и пароля должны быть не более 32 символов");
            return;
        }

        String authErrorMessage = network.sendAuthMessage(login, password);
        log.info("authErrorMessage - "+authErrorMessage);

        if (authErrorMessage == null) {
            chatApplication.openChatDialog();
        } else if (authErrorMessage.equals("Программа на вашем хост-компьютере разорвала установленное подключение")) {
            chatApplication.showErrorAlert("Таймаут подключения!", "Истекло время ожидания подключения!");
        } else {
            chatApplication.showErrorAlert("Ошибка аутентификации", authErrorMessage);

        }
    }

    @FXML
    void signUp(ActionEvent event) {

    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Network getNetwork() {
        return network;
    }

    public void setStartClient(ChatApplication chatApplication) {
        this.chatApplication = chatApplication;
    }

    public ChatApplication getStartClient() {
        return chatApplication;
    }
}
