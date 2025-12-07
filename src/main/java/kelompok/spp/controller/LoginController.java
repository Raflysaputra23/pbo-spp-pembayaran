/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import kelompok.spp.App;
import kelompok.spp.lib.Timeout;
import kelompok.spp.lib.VisibleNode;
import kelompok.spp.model.LoginModel;

/**
 *
 * @author rafly
 */
public class LoginController implements Initializable {

    private AuthController rootPane;

    @FXML
    TextField username;

    @FXML
    TextField password;

    @FXML
    Button submit;

    @FXML
    HBox alertBox;

    @FXML
    Text alertText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        VisibleNode.hideNode(alertBox);
    }

    public void setRoot(AuthController root) {
        this.rootPane = root;
    }

    @FXML
    public void handleLogin(ActionEvent e) throws SQLException, IOException {
        Button button = (Button) e.getSource();
        String btn = button.getId();
        boolean response = LoginModel.login(username.getText(), password.getText(), btn);
        VisibleNode.showNodeTimer(alertBox, 5);
        clearText();
        if (response) {
            alertBox.setStyle("-fx-background-color: #00ff00; -fx-background-radius: 8");
            alertText.setText("Login berhasil");

            Timeout.setTimeout(() -> {
                try {
                    App.setRoot("home");
                } catch (IOException ex) {
                    System.out.println("Gagal membuka page: " + ex);
                }
            }, 2);
        } else {
            alertBox.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 8");
            alertText.setText("Login gagal");
        }
    }

    @FXML
    public void onChange(KeyEvent e) {
        if (username.getText().length() < 4 || password.getText().length() < 4) {
            submit.setDisable(true);
        } else {
            submit.setDisable(false);
        }
    }

    @FXML
    public void handlePage(ActionEvent e) throws IOException {
        this.rootPane.handlePage(e, "registerAdmin");
    }
    
    public void clearText() {
        username.setText("");
        password.setText("");
    }
}
