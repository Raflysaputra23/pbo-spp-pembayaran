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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import kelompok.spp.lib.FormatNumber;
import kelompok.spp.lib.UserIsLogin;
import kelompok.spp.model.HistoryModel;
import kelompok.spp.model.object.UserModel;

/**
 *
 * @author rafly
 */
public class HistoryController implements Initializable {

    private final UserModel userIsLogin = UserIsLogin.getSession();

    @FXML
    private Button btnSorting;

    @FXML
    private TextField inputSearch;

    @FXML
    private TilePane containerHistory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnSorting.setOnAction(e -> {
            String text = btnSorting.getId();
            handleSorting(text);
        });

        ObservableList<Map<String, Object>> dataHistory;
        if (userIsLogin != null && userIsLogin.getRole().equals("user")) {
            dataHistory = HistoryModel.getDataHistoryById(userIsLogin.getUserId(), "ASC");
        } else {
            dataHistory = HistoryModel.getDataHistoryAll("ASC");
        }
        renderCardHistory(dataHistory);
    }

    private void handleSorting(String sort) {

        ObservableList<Map<String, Object>> dataHistory;
        if (userIsLogin != null && userIsLogin.getRole().equals("user")) {
            dataHistory = HistoryModel.getDataHistoryById(userIsLogin.getUserId(), sort);
        } else {
            dataHistory = HistoryModel.getDataHistoryAll(sort);
        }

        if (sort.equals("ASC")) {
            btnSorting.setText("TERLAMA");
            btnSorting.setId("DESC");
        } else {
            btnSorting.setText("TERBARU");
            btnSorting.setId("ASC");
        }
        renderCardHistory(dataHistory);
    }

    private void renderCardHistory(ObservableList<Map<String, Object>> dataHistory) {
        containerHistory.getChildren().clear();

        for (Map<String, Object> data : dataHistory) {
            VBox card = new VBox();
            card.setSpacing(4);
            card.prefWidth(200);
            card.prefHeight(Region.USE_COMPUTED_SIZE);
            card.setFillWidth(true);
            card.getStyleClass().add("card");

            VBox title = new VBox();
            title.setFillWidth(true);
            title.setAlignment(Pos.CENTER);
            Text subtitle = new Text();
            subtitle.setText("Aplikasi Pembayaran");
            subtitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-font-style: italic;");
            subtitle.getStyleClass().add("poppins-semibold");
            Text subtitle2 = new Text();
            subtitle2.setText("SPP");
            subtitle2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-font-style: italic; -fx-fill: #0000ff;");
            subtitle2.getStyleClass().add("poppins-semibold");
            title.getChildren().addAll(subtitle, subtitle2);

            VBox deskripsi = new VBox();
            deskripsi.setFillWidth(true);
            deskripsi.getStyleClass().add("poppins-regular");
            Text text1 = new Text();
            text1.setText("No. Tranksaksi : " + data.get("TranksaksiID"));
            text1.setStyle("-fx-font-size: 10px;");
            Text text2 = new Text();
            text2.setText("Metode Pay : " + data.get("MetodePay"));
            text2.setStyle("-fx-font-size: 10px;");
            Text text3 = new Text();
            text3.setText("Waktu Tranksaksi : " + data.get("CreateAt"));
            text3.setStyle("-fx-font-size: 10px;");
            Text text4 = new Text();
            text4.setText("NISN : " + data.get("Nisn"));
            text4.setStyle("-fx-font-size: 10px;");
            deskripsi.getChildren().addAll(text1, text2, text3, text4);

            Separator line = new Separator();

            HBox header = new HBox();
            header.getStyleClass().add("poppins-semibold");
            Text bulan = new Text();
            bulan.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            bulan.setText("Bulan");
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            Text harga = new Text();
            harga.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            harga.setText("Harga");
            header.getChildren().addAll(bulan, spacer, harga);

            String[] NamaBulan = ((String) data.get("NamaBulan")).split(", ");
            String[] Harga = ((String) data.get("Harga")).split(", ");

            VBox containerBody = new VBox();
            containerBody.setFillWidth(true);
            containerBody.getStyleClass().add("poppins-regular");
            for (int i = 0; i < Harga.length; i++) {
                HBox body = new HBox();
                Text bulan1 = new Text();
                bulan1.setStyle("-fx-font-size: 12px;");
                bulan1.setText(NamaBulan[i]);
                Region spacer1 = new Region();
                HBox.setHgrow(spacer1, Priority.ALWAYS);
                Text harga1 = new Text();
                harga1.setStyle("-fx-font-size: 12px;");
                harga1.setText("Rp. " + FormatNumber.format(Harga[i]));
                body.getChildren().addAll(bulan1, spacer1, harga1);
                containerBody.getChildren().add(body);
            }

            HBox totalHarga = new HBox();
            totalHarga.getStyleClass().add("poppins-semibold");
            Text bulan2 = new Text();
            bulan2.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            bulan2.setText("Total Harga");
            Region spacer2 = new Region();
            HBox.setHgrow(spacer2, Priority.ALWAYS);
            Text harga2 = new Text();
            harga2.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
            harga2.setText("Rp. " + FormatNumber.format(String.valueOf(data.get("TotalHarga"))));
            totalHarga.getChildren().addAll(bulan2, spacer2, harga2);

            Separator line2 = new Separator();

            VBox footer = new VBox();
            footer.getStyleClass().add("poppins-semibold");
            footer.setAlignment(Pos.CENTER);
            footer.setFillWidth(true);
            Text textFooter = new Text();
            textFooter.setText("Terima kasih sudah membayar tepat waktu");
            textFooter.setStyle("-fx-font-size: 11px; -fx-font-weight: bold;");
            textFooter.setWrappingWidth(180);
            textFooter.setTextAlignment(TextAlignment.CENTER);
            footer.getChildren().add(textFooter);

            card.getChildren().addAll(title, deskripsi, line, header, containerBody, totalHarga, line2, footer);
            containerHistory.getChildren().add(card);
        }
    }

    @FXML
    public void handleSearch(KeyEvent e) {
        if (userIsLogin.getRole().equals("admin")) {
            ObservableList<Map<String, Object>> dataHistory = HistoryModel.searchDataHistoryAll(inputSearch.getText());
            renderCardHistory(dataHistory);
        } else {
            String id = userIsLogin.getUserId();
             ObservableList<Map<String, Object>> dataHistory = HistoryModel.searchDataHistoryAll(inputSearch.getText(), id);
            renderCardHistory(dataHistory);
        }

    }

}
