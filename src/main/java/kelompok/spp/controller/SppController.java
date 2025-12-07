/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import kelompok.spp.lib.FormatNumber;
import kelompok.spp.model.SppModel;

/**
 *
 * @author rafly
 */
public class SppController implements Initializable {
    
    private RootController rootPane; 

    @FXML
    private TextField inputSearch;

    @FXML
    private TilePane containerCard;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }
    
    public void loadData() {
        ObservableList<Map<String, Object>> dataSpp = SppModel.getDataSppAll();
        renderCard(dataSpp);
    }

    @FXML
    public void onSearch(KeyEvent e) {
        ObservableList<Map<String, Object>> dataSpp = SppModel.searchDataSpp(inputSearch.getText());
        renderCard(dataSpp);
    }
    
    public void setRoot(RootController root) {
        this.rootPane = root;
    }

    public void renderCard(ObservableList<Map<String, Object>> dataSpp) {
        containerCard.getChildren().clear();

        for (Map<String, Object> data : dataSpp) {
            HBox card = new HBox();
            card.getStyleClass().add("card");
            card.setAlignment(Pos.CENTER);
            card.setPrefHeight(140);
            card.setPrefWidth(300);
            card.setSpacing(5);

            ImageView img = new ImageView();
            img.setImage(new Image(getClass().getResource("/assets/user.jpg").toString()));
            img.setFitWidth(107);
            img.setFitHeight(120);
            HBox.setHgrow(img, Priority.ALWAYS);

            VBox box = new VBox();
            HBox.setHgrow(box, Priority.ALWAYS);
            box.setFillWidth(true);
            box.setSpacing(5);
            box.setAlignment(Pos.CENTER_LEFT);

            VBox boxAtas = new VBox();
            boxAtas.setAlignment(Pos.BOTTOM_LEFT);
            VBox.setVgrow(boxAtas, Priority.ALWAYS);
            boxAtas.setFillWidth(true);

            Text Nama = new Text();
            Nama.setText(data.get("NamaLengkap").toString());
            Nama.getStyleClass().add("poppins-semibold");
            Nama.setStyle("-fx-font-size: 14px; -fx-font-weight: bold");

            Text Kelas = new Text();
            Kelas.setText(data.get("Kelas").toString() + " " + data.get("SingkatanJurusan").toString());
            Kelas.getStyleClass().add("poppins-regular");
            boxAtas.getChildren().addAll(Nama, Kelas);

            VBox boxBawah = new VBox();
            boxBawah.setAlignment(Pos.TOP_LEFT);
            VBox.setVgrow(boxBawah, Priority.ALWAYS);
            boxBawah.setFillWidth(true);

            Text Harga = new Text();
            Harga.setText("Rp. " + FormatNumber.format(data.get("HargaJurusan").toString()) + " - SPP");
            Harga.getStyleClass().add("poppins-regular");

            Button btnDetail = new Button();
            btnDetail.getStyleClass().addAll("bg-primary", "poppins-semibold");
            btnDetail.setText("Detail");
            btnDetail.setCursor(Cursor.HAND);

            btnDetail.setOnAction(e -> {
                try {
                    this.rootPane.handleDetailPage(data.get("Nisn").toString());
                } catch (IOException ex) {
                    System.getLogger(SppController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            });

            boxBawah.getChildren().addAll(Harga, btnDetail);
            box.getChildren().addAll(boxAtas, boxBawah);
            card.getChildren().addAll(img, box);

            containerCard.getChildren().add(card);
        }

    }
}
