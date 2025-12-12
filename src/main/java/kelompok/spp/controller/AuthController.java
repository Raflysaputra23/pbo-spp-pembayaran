/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import kelompok.spp.App;
import kelompok.spp.lib.UserIsLogin;

/**
 *
 * @author rafly
 */
public class AuthController implements Initializable {

    @FXML
    BorderPane rootPane;

    private LoginController login;
    private LoginController loginAdmin;
    private RegisterAdminController registerAdmin;
    private RegisterController register;
    private Parent viewLogin;
    private Parent viewLoginAdmin;
    private Parent viewRegisterAdmin;
    private Parent viewRegister;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (UserIsLogin.getSession() != null) {
            try {
                App.setRoot("home");
            } catch (IOException ex) {
                System.getLogger(AuthController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }

        try {
            FXMLLoader loginAdminLoader = App.loadFXML("loginAdmin");
            this.viewLoginAdmin = loginAdminLoader.load();
            this.loginAdmin = loginAdminLoader.getController();
            this.loginAdmin.setRoot(this);
            
            FXMLLoader loginLoader = App.loadFXML("login");
            this.viewLogin = loginLoader.load();
            this.login = loginLoader.getController();
            this.login.setRoot(this);

            FXMLLoader registerAdminLoader = App.loadFXML("registerAdmin");
            this.viewRegisterAdmin = registerAdminLoader.load();
            this.registerAdmin = registerAdminLoader.getController();
            this.registerAdmin.setRoot(this);

            FXMLLoader registerLoader = App.loadFXML("register");
            this.viewRegister = registerLoader.load();
            this.register = registerLoader.getController();
            this.register.setRoot(this);

            rootPane.setCenter(this.viewLogin);
        } catch (IOException ex) {
            System.getLogger(AuthController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public void handlePage(ActionEvent e, String page) throws IOException {
        switch (page) {
            case "login":
                rootPane.setCenter(this.viewLogin);
                break;
            case "loginAdmin":
                rootPane.setCenter(this.viewLoginAdmin);
                break;
            case "registerAdmin":
                rootPane.setCenter(this.viewRegisterAdmin);
                break;
            default:
                VBox container = new VBox(this.viewRegister);
                container.setAlignment(Pos.CENTER);
                container.setPadding(new Insets(10));

                ScrollPane scroll = new ScrollPane(container);
                scroll.setFitToWidth(true);       
                scroll.setFitToHeight(true);   

                VBox.setVgrow(scroll, Priority.ALWAYS);

                rootPane.setCenter(scroll);
                break;
        }
    }
}
