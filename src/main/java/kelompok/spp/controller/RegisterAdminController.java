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
import kelompok.spp.lib.Timeout;
import kelompok.spp.lib.VisibleNode;
import kelompok.spp.model.RegisterModel;

/**
 *
 * @author rafly
 */
public class RegisterAdminController implements Initializable {

    private AuthController rootPane;

    @FXML
    TextField username;

    @FXML
    TextField namaLengkap;

    @FXML
    TextField email;

    @FXML
    TextField password;

    @FXML
    TextField confirmPassword;

    @FXML
    Text validasiConfirmPassword;

    @FXML
    Button submit;

    @FXML
    Button daftarSiswa;

    @FXML
    HBox alertBox;

    @FXML
    Text alertText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        VisibleNode.hideNode(alertBox);
        VisibleNode.hideNode(validasiConfirmPassword);

        daftarSiswa.setOnAction(e -> {
            try {
                this.rootPane.handlePage(e, "register");
            } catch (IOException ex) {
                System.out.println("Error page: " + ex);
            }
        });
    }

    public void setRoot(AuthController root) {
        this.rootPane = root;
    }

    @FXML
    public void handleRegister(ActionEvent e) throws SQLException, IOException {
        Button button = (Button) e.getSource();
        String btn = button.getId();
        boolean response = RegisterModel.registerAdmin(username.getText(), namaLengkap.getText(), email.getText(), password.getText(), btn);
        VisibleNode.showNodeTimer(alertBox, 5);
        clearText();
        if (response) {
            alertBox.setStyle("-fx-background-color: #00ff00; -fx-background-radius: 8");
            alertText.setText("Register berhasil");
            Timeout.setTimeout(() -> {
                try {
                    this.rootPane.handlePage(e, "loginAdmin");
                } catch (IOException ex) {
                    System.getLogger(RegisterController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }, 2);
        } else {
            alertBox.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 8");
            alertText.setText("Register gagal");
        }
    }

    @FXML
    public void onChange(KeyEvent e) {
        String passwordConfirm = confirmPassword.getText();
        if (username.getText().length() >= 4 && password.getText().length() >= 4 && passwordConfirm.equals(password.getText())) {
            submit.setDisable(false);
        } else {
            submit.setDisable(true);
        }
    }

    public void onConfirmPassword(KeyEvent e) {
        String passwordConfirm = confirmPassword.getText();
        if (username.getText().length() >= 4 && password.getText().length() >= 4 && passwordConfirm.equals(password.getText())) {
            submit.setDisable(false);
            VisibleNode.hideNode(validasiConfirmPassword);
        } else {
            submit.setDisable(true);
            VisibleNode.showNode(validasiConfirmPassword);
            validasiConfirmPassword.setText("Password tidak sama");
            validasiConfirmPassword.setStyle("-fx-fill: #ff0000");
        }
    }

    public void handlePage(ActionEvent e) throws IOException {
        this.rootPane.handlePage(e, "loginAdmin");
    }
    
    public void clearText() {
        username.setText("");
        namaLengkap.setText("");
        email.setText("");
        password.setText("");
        confirmPassword.setText("");
    }

}
