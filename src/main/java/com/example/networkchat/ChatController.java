package com.example.networkchat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class ChatController {
    String nickName = "You";
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textInputField;

    @FXML
    public void buttonSend() {
        String text = textInputField.getText();
        if (!textInputField.getText().isEmpty()) {
            addMessage(text);
            textInputField.clear();
        }
    }

    public void addMessage(String message) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        textArea.appendText(nickName + " (" + dtf.format(now) + "): \n" + message + "\n");
        textArea.selectPositionCaret(textArea.getLength());
    }

    public void handleEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            buttonSend();
        }
    }

    @FXML
    void initialize() {
        textArea.setEditable(false);

    }

}

