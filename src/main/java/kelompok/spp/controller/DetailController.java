/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import kelompok.spp.model.DetailModel;

/**
 *
 * @author rafly
 */
public class DetailController implements Initializable {

    private String Nisn;

    private RootController rootPane;

    @FXML
    private TilePane containerCard;

    @FXML
    private Text judul;

    @FXML
    private Button btnKembali;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnKembali.setOnAction(e -> {
            this.rootPane.handleBack();
        });
    }

    public void setRoot(RootController root) {
        this.rootPane = root;
    }

    public void setNisn(String nisn) {
        this.Nisn = nisn;
        loadData();
    }

    public void loadData() {
        if (this.Nisn == null) {
            return;
        }
//        SAMPAI SINI
        ObservableList<Map<String, Object>> dataBulan = DetailModel.getSppById(this.Nisn);
        Map<String, Object> namaSiswa = DetailModel.getNama(this.Nisn);

        judul.setText(namaSiswa.get("NamaLengkap").toString());
        renderCard(dataBulan);
    }

    private void renderCard(ObservableList<Map<String, Object>> dataBulan) {
        containerCard.getChildren().clear();
        for (Map<String, Object> data : dataBulan) {
            VBox card = new VBox();
            card.setAlignment(Pos.CENTER);
            card.getStyleClass().add("card");
            card.setPrefWidth(240);
            card.setPrefHeight(120);
            card.setSpacing(6);
            card.setFillWidth(false);

            Text bulan = new Text();
            bulan.setText(data.get("NamaBulan").toString());
            bulan.getStyleClass().add("poppins-semibold");
            bulan.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");

            Text keterangan = new Text();
            keterangan.setText(data.get("Lunas") == null ? "Belum Lunas" : "Lunas");
            keterangan.getStyleClass().add("poppins-semibold");
//            String warnaBg = (data.get("Lunas") == null) ? "-fx-fill: #ff0000" : "-fx-fill: #00ff00";
            keterangan.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: #fff");

            HBox boxText = new HBox(keterangan);
            boxText.setAlignment(Pos.CENTER);
            boxText.setPadding(new Insets(4, 8, 4, 8));
            String warnaBgBox = (data.get("Lunas") == null) ? "-fx-background-color: #ff0000" : "-fx-background-color: #00ff00";
            boxText.setStyle("-fx-background-radius: 8; "+warnaBgBox);     

            card.getChildren().addAll(bulan, boxText);
            containerCard.getChildren().add(card);
        }
    }

}
