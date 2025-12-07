package kelompok.spp.controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import kelompok.spp.lib.FormatNumber;
import kelompok.spp.lib.UserIsLogin;
import kelompok.spp.lib.VisibleNode;
import kelompok.spp.model.DashboardModel;
import kelompok.spp.model.object.UserModel;
import org.kordamp.ikonli.javafx.FontIcon;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author rafly
 */
public class DashboardController implements Initializable {

    private UserModel userIsLogin = UserIsLogin.getSession();

    @FXML
    private Text totalSemuaSiswa;

    @FXML
    private VBox boxSemua;

    @FXML
    private Text totalSiswa;

    @FXML
    private VBox boxSiswa;

    @FXML
    private Text totalSiswi;

    @FXML
    private VBox boxSiswi;

    @FXML
    private Text totalHistory;

    @FXML
    private VBox boxHistory;

    @FXML
    private TilePane containerSiswa;

    @FXML
    private Text totalLunas;

    @FXML
    private Text textHarga;

    @FXML
    private HBox boxHarga;

    @FXML
    private VBox boxBayar;

    @FXML
    private VBox boxDibayar;

    @FXML
    private Text totalBayar;

    @FXML
    private Text textBayar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Map<String, Object> data;
        if (userIsLogin.getRole().equals("user")) {
            Map<String, Object> statusBayaran = DashboardModel.getBayaranBulanIni(userIsLogin.getUserId());
            Map<String, Object> hargaJurusan = DashboardModel.getHargaJurusan(userIsLogin.getUserId());
            ObservableList<Map<String, Object>> statusBayaranSemua = DashboardModel.getBayaranSemuaBulan(userIsLogin.getUserId());
            data = DashboardModel.getTotalData(userIsLogin.getUserId());
            totalLunas.setText("Rp. " + FormatNumber.format(hargaJurusan.get("HargaJurusan").toString()));
            String warnaBox = statusBayaran == null ? "-fx-background-color: #ff0000" : "-fx-background-color: #00ff00";
            boxHarga.setStyle("-fx-background-radius: 10; " + warnaBox);
            textHarga.setText(statusBayaran == null ? "Belum Lunas" : "Lunas");

            int hargaJ = Integer.parseInt(hargaJurusan.get("HargaJurusan").toString());
            int sppYangDibayar = hargaJ * statusBayaranSemua.size();
            int sppYangHarusDibayar = hargaJ * (12 - statusBayaranSemua.size());

            totalBayar.setText("Rp. " + FormatNumber.format(String.valueOf(sppYangDibayar)));
            textBayar.setText("Rp. -" + FormatNumber.format(String.valueOf(sppYangHarusDibayar)));
            FontIcon iconBayar = new FontIcon("fas-money-bill-wave");
            iconBayar.setIconSize(35);
            iconBayar.setIconColor(Paint.valueOf("#fff"));
            boxBayar.getChildren().add(iconBayar);

            FontIcon iconDibayar = new FontIcon("fas-money-check");
            iconDibayar.setIconSize(35);
            iconDibayar.setIconColor(Paint.valueOf("#fff"));
            boxDibayar.getChildren().add(iconDibayar);
        } else {
            data = DashboardModel.getTotalData();
            VisibleNode.hideNode(containerSiswa);
        }

        totalSemuaSiswa.setText(data.get("totalSemua").toString());
        totalSiswa.setText(data.get("totalSiswa").toString());
        totalSiswi.setText(data.get("totalSiswi").toString());
        totalHistory.setText(data.get("totalHistory").toString());

        FontIcon iconSemua = new FontIcon("fas-user-friends");
        iconSemua.setIconSize(35);
        iconSemua.setIconColor(Paint.valueOf("#fff"));
        boxSemua.getChildren().add(iconSemua);

        FontIcon iconSiswa = new FontIcon("fas-user");
        iconSiswa.setIconSize(35);
        iconSiswa.setIconColor(Paint.valueOf("#fff"));
        boxSiswa.getChildren().add(iconSiswa);

        FontIcon iconSiswi = new FontIcon("fas-user");
        iconSiswi.setIconSize(35);
        iconSiswi.setIconColor(Paint.valueOf("#fff"));
        boxSiswi.getChildren().add(iconSiswi);

        FontIcon iconHistory = new FontIcon("fas-history");
        iconHistory.setIconSize(35);
        iconHistory.setIconColor(Paint.valueOf("#fff"));
        boxHistory.getChildren().add(iconHistory);

    }

}
