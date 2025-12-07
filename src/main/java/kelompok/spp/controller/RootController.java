/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package kelompok.spp.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import kelompok.spp.App;
import kelompok.spp.lib.Alerts;
import kelompok.spp.lib.UserIsLogin;
import kelompok.spp.lib.VisibleNode;
import kelompok.spp.model.object.UserModel;
import org.w3c.dom.events.MouseEvent;

/**
 * FXML Controller class
 *
 * @author rafly
 */
public class RootController implements Initializable {

    private final UserModel userIslogin = UserIsLogin.getSession();

    private DetailController detail;
    private SppController spp;
    private Parent profilView;
    private Parent sppView;
    private Parent detailView;

//    ROOT PANE
    @FXML
    private BorderPane rootPane;

//    NAVBAR PANE
    @FXML
    private HBox navbarPane;

    @FXML
    private Text titleNavbar;

    @FXML
    private Text subtitleNavbar;

    @FXML
    private HBox logoNavbar;

//    SIDEBAR PANE
    @FXML
    private VBox asidePane;

    @FXML
    private VBox menuAside;

    @FXML
    private Hyperlink dashboard;

    @FXML
    private Hyperlink siswa;

    @FXML
    private Hyperlink pembayaran;

    @FXML
    private Hyperlink history;
    
    @FXML
    private Hyperlink manajemen;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        CEK SESSION
        try {
            if (UserIsLogin.getSession() == null) {
                App.setRoot("auth");
            }

            Parent dashboard = App.loadFXML("dashboard").load();
            rootPane.setCenter(dashboard);

            FXMLLoader sppPage = App.loadFXML("spp");
            this.sppView = sppPage.load();
            this.spp = sppPage.getController();
            this.spp.setRoot(this);

            FXMLLoader detailPage = App.loadFXML("detail");
            this.detailView = detailPage.load();
            this.detail = detailPage.getController();
            this.detail.setRoot(this);

            FXMLLoader profilPage = App.loadFXML("Profil");
            this.profilView = profilPage.load();

            logoNavbar.setOnMouseClicked(e -> {
                handleProfil();
            });

        } catch (IOException ex) {
            System.getLogger(RootController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        titleNavbar.setText(userIslogin.getUsername());
        if (userIslogin.getRole().equals("admin")) {
            VisibleNode.hideNode(subtitleNavbar);
        } else {
            String nisn = userIslogin.getUserId();
            subtitleNavbar.setText(nisn);
        }

        for (Node node : menuAside.getChildren()) {
            if (node instanceof Hyperlink) {
                Hyperlink links = (Hyperlink) node;
                if (userIslogin.getRole().equals("user") && (links.getText().equals("Spp") || links.getText().equals("Manajemen"))) {
                    links.setVisible(false);
                    links.setManaged(false);
                }
                if (userIslogin.getRole().equals("admin") && links.getText().equals("Pembayaran")) {
                    links.setVisible(false);
                    links.setManaged(false);
                }
            }
        }
        

    }

    @FXML
    public void setPage(ActionEvent e) throws IOException {
        Hyperlink link = (Hyperlink) e.getSource();
        String page = link.getId();

//        HAPUS SEMUA WARNA ACTIVE MENU LINKNYA
        for (Node node : menuAside.getChildren()) {
            if (node instanceof Hyperlink) {
                Hyperlink links = (Hyperlink) node;
                links.getStyleClass().remove("bg-primary");
                links.setStyle(links.getStyle() + ";-fx-text-fill: #000;");
            }
        }

//        SET WARNA ACTIVE KE MENU SAAT INI
        link.getStyleClass().add("bg-primary");
        link.setStyle(link.getStyle() + "-fx-text-fill: #fff");
        if (page.equals("spp")) {
            spp.loadData();
            rootPane.setCenter(this.sppView);
        } else {
            Parent newPage = App.loadFXML(page).load();
            rootPane.setCenter(newPage);
        }
    }

    @FXML
    public void handleLogout(ActionEvent e) throws IOException {
        Alerts alert = new Alerts();
        boolean result = alert.alertConfirmation("Konfirmasi", "Apakah anda yakin ingin keluar dari halaman ini?");
        if (result) {
            UserIsLogin.deleteSession();
            App.setRoot("auth");
        }
    }

    public void handleProfil() {
        rootPane.setCenter(profilView);
    }

    public void handleDetailPage(String nisn) throws IOException {
        detail.setNisn(nisn);
        rootPane.setCenter(detailView);
    }

    public void handleBack() {
        rootPane.setCenter(sppView);
    }

}
