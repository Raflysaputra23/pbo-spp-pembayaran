/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import kelompok.spp.lib.Alerts;
import kelompok.spp.lib.Bycript;
import kelompok.spp.lib.UserIsLogin;
import kelompok.spp.lib.VisibleNode;
import kelompok.spp.model.ProfilModel;
import kelompok.spp.model.object.JurusanModel;
import kelompok.spp.model.object.UserModel;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 *
 * @author rafly
 */
public class ProfilController implements Initializable {

    private UserModel userIsLogin = UserIsLogin.getSession();
    private Map<String, Object> dataUser = ProfilModel.getDataUser(userIsLogin.getUserId(), userIsLogin.getRole());

    @FXML
    private TextField username;

    @FXML
    private Label labelNisn;

    @FXML
    private TextField email;

    @FXML
    private TextField noTelp;

    @FXML
    private HBox grupKelas;

    @FXML
    private ComboBox<String> kelas;

    @FXML
    private ComboBox<JurusanModel> jurusan;

    @FXML
    private HBox grupJenkel;

    @FXML
    private ComboBox<String> jenkel;

    @FXML
    private DatePicker tanggalLahir;

    @FXML
    private TextField status;

    @FXML
    private VBox grupPassword;

    @FXML
    private PasswordField passwordLama;

    @FXML
    private PasswordField passwordBaru;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnSubmit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (userIsLogin.getRole().equals("admin")) {
            VisibleNode.hideNode(grupKelas);
            VisibleNode.hideNode(grupJenkel);
            labelNisn.setText("Username");
        } else {
            kelas.getItems().addAll("10", "11", "12");
            kelas.setValue(dataUser.get("Kelas").toString());

            ObservableList<Map<String, Object>> dataJurusan = ProfilModel.getJurusan();
            for (Map<String, Object> j : dataJurusan) {
                jurusan.getItems().add(new JurusanModel(j.get("JurusanID").toString(), j.get("SingkatanJurusan").toString()));
            }

            if (dataUser.get("TanggalLahir") != null) {
                LocalDate tglLahir = LocalDate.parse(dataUser.get("TanggalLahir").toString());
                tanggalLahir.setValue(tglLahir);
            }
            jurusan.setValue(new JurusanModel(dataUser.get("JurusanID").toString(), dataUser.get("SingkatanJurusan").toString()));
            jenkel.getItems().addAll("perempuan", "laki-laki");
            jenkel.setValue(dataUser.get("Jenkel") == null ? "" : dataUser.get("Jenkel").toString());
        }
        VisibleNode.hideNode(btnClose);
        VisibleNode.hideNode(btnSubmit);
        username.setText(dataUser.get("Username").toString());
        email.setText(dataUser.get("Email") == null ? "" : dataUser.get("Email").toString());
        noTelp.setText(dataUser.get("NoTelp") == null ? "" : dataUser.get("NoTelp").toString());
        status.setText(dataUser.get("Role").toString());

        FontIcon iconEdit = new FontIcon("fas-edit");
        iconEdit.setIconSize(8);
        iconEdit.setIconColor(Paint.valueOf("#fff"));

        btnEdit.setAlignment(Pos.CENTER);
        btnEdit.setText("");
        btnEdit.setGraphic(iconEdit);

    }

    @FXML
    private void handlePasswordOpen(ActionEvent e) {
        VisibleNode.showNode(grupPassword);
    }

    @FXML
    private void handlePasswordClose(ActionEvent e) {
        VisibleNode.hideNode(grupPassword);
    }

    @FXML
    private void handleEditOpen(ActionEvent e) {
        VisibleNode.hideNode(btnEdit);
        VisibleNode.showNode(btnClose);
        VisibleNode.showNode(btnSubmit);
        email.setDisable(false);
        noTelp.setDisable(false);
        kelas.setDisable(false);
        jurusan.setDisable(false);
        jenkel.setDisable(false);
        tanggalLahir.setDisable(false);
    }

    @FXML
    private void handleEditClose(ActionEvent e) {
        VisibleNode.showNode(btnEdit);
        VisibleNode.hideNode(btnClose);
        VisibleNode.hideNode(btnSubmit);
        email.setDisable(true);
        noTelp.setDisable(true);
        kelas.setDisable(true);
        jurusan.setDisable(true);
        jenkel.setDisable(true);
        tanggalLahir.setDisable(true);
    }

    @FXML
    private void handleSubmit(ActionEvent e) {
        Alerts alert = new Alerts();
        boolean result = alert.alertConfirmation("Konfirmasi", "Apakah anda yakin ingin mengubah data ini?");
        if (result) {
            if (userIsLogin.getRole().equals("user")) {
                boolean response = ProfilModel.updateDataUser(username.getText(), email.getText(), noTelp.getText(), kelas.getValue(), jurusan.getValue().id, jenkel.getValue(), tanggalLahir.getValue());
                if (response) {
                    alert.alertInformation("Informasi", "Data berhasil diubah");
                } else {
                    alert.alertInformation("Informasi", "Data gagal diubah");
                }
            } else {
                boolean response = ProfilModel.updateDataUser(userIsLogin.getUserId(), username.getText(), email.getText(), noTelp.getText(), status.getText());
                if (response) {
                    alert.alertInformation("Informasi", "Data berhasil diubah");
                } else {
                    alert.alertInformation("Informasi", "Data gagal diubah");
                }
            }

        }
        handleEditClose(e);
    }

    @FXML
    private void handleSubmitPassword(ActionEvent e) {
        Alerts alert = new Alerts();
        boolean result = alert.alertConfirmation("Konfirmasi", "Apakah anda yakin ingin mengubah password?");
        if (result && Bycript.passwordVerify(passwordLama.getText(), dataUser.get("Password").toString())) {
            if (passwordBaru.getText().equals(confirmPassword.getText())) {
                boolean response = ProfilModel.updateDataUser(userIsLogin.getUserId(), passwordBaru.getText(), userIsLogin.getRole());
                if (response) {
                    alert.alertInformation("Informasi", "Password berhasil diubah");
                } else {
                    alert.alertInformation("Informasi", "Password gagal diubah");
                }
            } else {
                alert.alertInformation("Informasi", "Gagal, Password tidak saman");
            }
        }
        passwordBaru.setText("");
        passwordLama.setText("");
        confirmPassword.setText("");
        handlePasswordClose(e);
    }

}
