package com.example.networkchat.controllers;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.networkchat.ChatApplication;
import com.example.networkchat.models.Network;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ChatController {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    @FXML
    private Label usernameTitle;
    @FXML
    private TextArea chatHistory;
    @FXML
    private TextField inputField;
    @FXML
    private Button sendButton;
    private Network network;
    private String selectedRecipient;

    private ChatApplication chatApplication;
    @FXML
    private Button changeNickButton;
    @FXML
    private TextField inputNewNick;


    @FXML
    private ListView<String> usersList;

    ObservableList<String> users = FXCollections.observableArrayList();


    @FXML
    void initialize() {

        usersList.editableProperty();
        usersList.setItems(users);

        changeNickButton.setOnAction(event -> changeNick());  //Смена ника!!!!
        inputNewNick.setOnAction(event -> changeNick());

        inputField.setOnAction(event -> sendMessage());
        inputField.setOnAction(event -> sendMessage());

        usersList.setCellFactory(lv -> {
            MultipleSelectionModel<String> selectionModel = usersList.getSelectionModel();
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                usersList.requestFocus();
                if (!cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (selectionModel.getSelectedIndices().contains(index)) {
                        selectionModel.clearSelection(index);
                        selectedRecipient = null;
                    } else {
                        selectionModel.select(index);
                        selectedRecipient = cell.getItem();
                    }
                    event.consume();
                }
            });
            return cell;
        });
        chatHistory.setEditable(false);
    }

    @FXML
    public void sendMessage() {
        String message = inputField.getText().trim();
        inputField.clear();

        if (message.isEmpty()) {
            return;
        }
        if (selectedRecipient != null) {
            network.sendPrivateMessage(selectedRecipient, message);
        } else {
            network.sendMessage(message);
        }
    }

    public void addMessage(String sender, String message) {
//
        chatHistory.appendText(dtf.format(now));
        chatHistory.appendText(System.lineSeparator());
        chatHistory.appendText(String.format("%s: %s", sender, message));
        chatHistory.appendText(System.lineSeparator());
        chatHistory.appendText(System.lineSeparator());


    }

    public void handleEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            sendMessage();
        }
    }

    public void appendServerMessage(String message) {
        chatHistory.appendText(String.format(message));
        chatHistory.appendText(System.lineSeparator());
        chatHistory.appendText(System.lineSeparator());
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void setUsernameTitle(String usernameTitleStr) {
        this.usernameTitle.setText(usernameTitleStr);
    }

    public void setStartClient(ChatApplication chatApplication) {
        this.chatApplication = chatApplication;
    }

    public ChatApplication getChatApplication() {
        return chatApplication;
    }

    public void statusUserInList(String username, String status) {
        if (status.equals("отключился")) {
            users.remove(username);
            System.out.println("users: " + users);
        }

        renewUsersList();


    }

    public void addUser(String username) {
        if (!users.contains(username)) {
            users.add(username);
            System.out.println(users);
        }
        renewUsersList();
//        log.info(" log usernameAdd= {} ",username);
    }


    public void renewUsersList() {
        usersList.getItems().removeAll();
        usersList.setItems(users);
        usersList.refresh();

    }

    @FXML
    void changeNick() {
        String newNick = inputNewNick.getText().trim();
        inputNewNick.clear();

        if (newNick.isEmpty()) {
            return;
        }
        log.info("НИК из поля ввода - " + newNick);

//        for (String u :users) {
//            if(u.equals(this.users)){

        int index = this.users.indexOf(this);
        log.info("index- " + ((index) + 1));
        this.users.set(index + 1, newNick);
        setUsernameTitle(newNick);
        network.sendServerMessage(newNick);
        renewUsersList();

    }

    public void writeHistoryChat() {
        try {
            File history = new File("src/main/resources/lib/chatHistory.txt");
            if (!history.exists()) {
                System.out.println("Файл не обнаружен!, создаем файл.");
                history.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream("src/main/resources/lib/chatHistory.txt");

            BufferedOutputStream bos = new BufferedOutputStream(fos);

            log.info("ЗАПИСЬ чата в файл: " + chatHistory.getText());
            byte[] bytes = chatHistory.getText().getBytes();

            bos.write(bytes);
            bos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readHistoryChat() throws IOException {
        int posHistory = 100;
        List<String> historyList = new ArrayList<>();
        FileInputStream fis = new FileInputStream("src/main/resources/lib/chatHistory.txt");

        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String temp;
        while ((temp = br.readLine()) != null) {
            historyList.add(temp);
        }

        if (historyList.size() > posHistory) {
            for (int i = historyList.size() - posHistory; i <= (historyList.size() - 1); i++) {
                chatHistory.appendText(historyList.get(i) + "\n");
                log.info("Добавлено в чат= " + historyList.get(i) + "\n");
            }
        } else
            for (String msg : historyList) {
                chatHistory.appendText(msg + "\n");
            }
    }
}









