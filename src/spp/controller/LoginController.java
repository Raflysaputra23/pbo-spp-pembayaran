package spp.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class LoginController {
    @FXML
    private TextField Username;
    
    @FXML
    private Text usernameValidasi;
    
    @FXML
    private TextField Password;
    
    @FXML
    private Text passwordValidasi;

    @FXML
    private void handleSubmit(ActionEvent event) {
        System.out.printf("Username anda: %s\n", Username.getText());
        System.out.printf("Password anda: %s\n", Password.getText());
    }
    
    @FXML
    private void handleUsername(KeyEvent event) {
        if(Username.getText().length() < 4) {
            usernameValidasi.setVisible(true);
            usernameValidasi.setText("Username must 4 character");
        } else {
            usernameValidasi.setVisible(false);
        }
    }
    
    @FXML
    private void handlePassword(KeyEvent event) {
        if(Password.getText().length() < 4) {
            passwordValidasi.setVisible(true);
            passwordValidasi.setText("Password must 4 character");
        } else {
            passwordValidasi.setVisible(false);
        }
    }
}
